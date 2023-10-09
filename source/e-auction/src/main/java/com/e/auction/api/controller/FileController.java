package com.e.auction.api.controller;

import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.UploadFileDto;
import com.e.auction.api.view.dto.aws.FileS3Dto;
import com.e.auction.api.view.form.UploadFileForm;
import com.e.auction.api.service.EAuctionApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/v1/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class FileController {
    @Autowired
    EAuctionApiService eAuctionApiService;

    @PostMapping(value = "/upload/s3", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<UploadFileDto> upload(@Valid UploadFileForm uploadFileForm, BindingResult bindingResult) {
        return eAuctionApiService.uploadFileS3(uploadFileForm);
    }

    @GetMapping(value = "/load/s3/{folder}/{fileName:.+}")
    @Cacheable("images")
    public ResponseEntity<?> loadFileS3(@PathVariable String folder, @PathVariable String fileName) {
        FileS3Dto fileS3Dto = eAuctionApiService.loadFileAsResource(folder, fileName);
        if (fileS3Dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentLength(fileS3Dto.getFileByte().length)
                .contentType(MediaType.parseMediaType(fileS3Dto.getFileType()))
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .body(fileS3Dto.getFileByte());
    }

    @GetMapping(value = "/download/s3/{fileName:.+}")
    @Cacheable("images")
    public ResponseEntity<ByteArrayResource> downloadFileS3(@PathVariable String folder, @PathVariable String fileName) {
        byte[] data = eAuctionApiService.loadFileAsResource(folder, fileName).getFileByte();
        if (data == null) {
            return ResponseEntity.notFound().build();
        }
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .cacheControl(CacheControl.maxAge(7776000, TimeUnit.SECONDS))
                .body(resource);
    }
}
