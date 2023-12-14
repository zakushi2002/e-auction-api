package com.e.auction.api.service;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.utils.AWSCloudUtil;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.UploadFileDto;
import com.e.auction.api.view.dto.aws.FileS3Dto;
import com.e.auction.api.view.form.UploadFileForm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class EAuctionApiService {
    static final String[] UPLOAD_TYPES = new String[]{"AVATAR"}; // "LOGO", "AVATAR", "IMAGE", "DOCUMENT", "PRODUCT"
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.credentials.access.key}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret.key}")
    private String secretKey;
    @Value("${cloud.aws.s3.endpoint.url}")
    private String endpointUrl;
    @Autowired
    CommonAsyncService commonAsyncService;
    @Autowired
    OTPService OTPService;
    private final AWSCloudUtil awsCloudUtil = new AWSCloudUtil();

    public ApiMessageDto<UploadFileDto> uploadFileS3(UploadFileForm uploadFileForm) {
        ApiMessageDto<UploadFileDto> apiMessageDto = new ApiMessageDto<>();
        try {
            boolean contains = Arrays.stream(UPLOAD_TYPES).anyMatch(uploadFileForm.getType()::equalsIgnoreCase);
            if (!contains) {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("File type is not supported");
                return apiMessageDto;
            }
            uploadFileForm.setType(uploadFileForm.getType().toUpperCase());
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(uploadFileForm.getFile().getOriginalFilename()));
            String ext = FilenameUtils.getExtension(fileName);
            if (!Objects.equals(ext, "jpg") && !Objects.equals(ext, "jpeg") && !Objects.equals(ext, "png") && !Objects.equals(ext, "gif")) {
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("File extension is not supported");
                return apiMessageDto;
            }
            String finalFile = uploadFileForm.getType() + "_" + RandomStringUtils.randomAlphanumeric(10) + "." + ext;
            String bucketFolder = bucketName + "/" + uploadFileForm.getType().trim().toLowerCase();
            awsCloudUtil.uploadFile(finalFile, uploadFileForm.getFile().getBytes(), accessKey, secretKey, bucketFolder);
            String fileUrl = endpointUrl + bucketFolder + "/" + finalFile;
            UploadFileDto uploadFileDto = new UploadFileDto();
            uploadFileDto.setFilePath(fileUrl);
            apiMessageDto.setData(uploadFileDto);
            apiMessageDto.setMessage("Upload file success");
            return apiMessageDto;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage(e.getMessage());
        }
        apiMessageDto.setResult(false);
        apiMessageDto.setMessage("Upload file fail" + uploadFileForm.getFile().getOriginalFilename());
        return apiMessageDto;
    }

    public FileS3Dto loadFileAsResource(String folder, String fileName) {
        String bucketFolder = bucketName + "/" + folder.trim().toLowerCase();
        return awsCloudUtil.downloadFile(fileName, accessKey, secretKey, bucketFolder);
    }

    public void deleteFileS3(String folder, String fileName) {
        String bucketFolder = bucketName + "/" + folder.trim().toLowerCase();
        awsCloudUtil.deleteFile(fileName, accessKey, secretKey, bucketFolder);
    }

    public String getOTPForgetPassword() {
        return OTPService.generate(EAuctionConstant.OTP_LENGTH);
    }

    public void sendEmail(String email, Map<String, Object> variables, String subject) {
        commonAsyncService.sendEmail(email, variables, subject);
    }
}
