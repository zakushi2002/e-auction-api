package com.e.auction.api.controller;

import com.e.auction.api.model.Address;
import com.e.auction.api.model.criteria.AddressCriteria;
import com.e.auction.api.repository.AddressRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.address.AddressDto;
import com.e.auction.api.view.mapper.AddressMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/address")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AddressController extends BaseController {
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    AddressMapper addressMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<AddressDto>> listAddress(AddressCriteria addressCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<AddressDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Address> page = addressRepository.findAll(addressCriteria.getSpecification(), pageable);
        ResponseListDto<AddressDto> responseListObj = new ResponseListDto(addressMapper.fromEntityListToDtoList(page.getContent()), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list address success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AddressDto> getDetailAddress(@PathVariable String id) {
        ApiMessageDto<AddressDto> apiMessageDto = new ApiMessageDto<>();
        Address address = addressRepository.findById(id).orElse(null);
        if (address == null) {
            apiMessageDto.setMessage("Address not found");
            apiMessageDto.setCode(ErrorCode.ADDRESS_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(addressMapper.fromEntityToDtoWithChildren(address));
        apiMessageDto.setMessage("Get detail address success");
        return apiMessageDto;
    }
}
