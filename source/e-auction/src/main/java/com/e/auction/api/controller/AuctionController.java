package com.e.auction.api.controller;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.model.Account;
import com.e.auction.api.model.Auction;
import com.e.auction.api.model.Product;
import com.e.auction.api.model.criteria.AuctionCriteria;
import com.e.auction.api.repository.AccountRepository;
import com.e.auction.api.repository.AuctionRepository;
import com.e.auction.api.repository.ProductRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.auction.AuctionDto;
import com.e.auction.api.view.form.auction.CreateAuctionForm;
import com.e.auction.api.view.form.auction.UpdateAuctionForm;
import com.e.auction.api.view.mapper.AuctionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/auction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AuctionController extends BaseController {
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AuctionMapper auctionMapper;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<AuctionDto> createAuction(@Valid @RequestBody CreateAuctionForm createAuctionForm, BindingResult bindingResult) {
        ApiMessageDto<AuctionDto> apiMessageDto = new ApiMessageDto<>();
        Account seller = accountRepository.findById(getCurrentUser()).orElse(null);
        if (seller == null) {
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Seller not found");
            return apiMessageDto;
        }
        Product product = productRepository.findById(createAuctionForm.getProductId()).orElse(null);
        if (product == null || !product.getSeller().getId().equals(seller.getId())) {
            apiMessageDto.setCode(ErrorCode.PRODUCT_ERROR_NOT_FOUND_OR_NOT_BELONG_TO_SELLER);
            apiMessageDto.setMessage("Product not found or not belong to seller");
            return apiMessageDto;
        }
        Auction auction = auctionMapper.fromCreateFormToModel(createAuctionForm);
        auction.setEndDate(DateUtils.addHours(createAuctionForm.getStartDate(), product.getBidTime()));
        auction.setCurrentPrice(product.getStartBidPrice());
        auction.setSeller(seller);
        auction.setProduct(product);
        auction.setStatus(EAuctionConstant.STATUS_PENDING);
        auctionRepository.save(auction);
        apiMessageDto.setData(auctionMapper.fromEntityToDto(auction));
        apiMessageDto.setMessage("Create auction success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AuctionDto> getDetailAuction(@PathVariable Long id) {
        ApiMessageDto<AuctionDto> apiMessageDto = new ApiMessageDto<>();
        Auction auction = auctionRepository.findById(id).orElse(null);
        if (auction == null) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Auction not found");
            return apiMessageDto;
        }
        apiMessageDto.setData(auctionMapper.fromEntityToDto(auction));
        apiMessageDto.setMessage("Get detail auction success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<AuctionDto>> listAuction(AuctionCriteria auctionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<AuctionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Auction> auctionPage = auctionRepository.findAll(auctionCriteria.getSpecification(), pageable);
        ResponseListDto<AuctionDto> responseListObj = new ResponseListDto(auctionMapper.fromEntityToDtoList(auctionPage.getContent()), auctionPage.getTotalElements(), auctionPage.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list auction success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-client", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<AuctionDto>> listAuctionClient(AuctionCriteria auctionCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<AuctionDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Auction> auctionPage = auctionRepository.findAll(auctionCriteria.getSpecification(), pageable);
        ResponseListDto<AuctionDto> responseListObj = new ResponseListDto(auctionMapper.fromEntityToDtoClientList(auctionPage.getContent()), auctionPage.getTotalElements(), auctionPage.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list client auction success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<AuctionDto> updateStatusAuction(@RequestBody @Valid UpdateAuctionForm updateAuctionForm) {
        ApiMessageDto<AuctionDto> apiMessageDto = new ApiMessageDto<>();
        Account admin = accountRepository.findById(getCurrentUser()).orElse(null);
        if (admin == null) {
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Admin not found");
            return apiMessageDto;
        }
        Auction auction = auctionRepository.findById(updateAuctionForm.getId()).orElse(null);
        if (auction == null) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Auction not found");
            return apiMessageDto;
        }
        if (updateAuctionForm.getStatus().equals(EAuctionConstant.STATUS_PENDING)) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_STATUS_INVALID);
            apiMessageDto.setMessage("Status invalid");
            return apiMessageDto;
        }
        if (updateAuctionForm.getStatus().equals(EAuctionConstant.STATUS_ACTIVE) && !admin.getKind().equals(EAuctionConstant.ACCOUNT_KIND_ADMIN)) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_ADMIN_APPROVE);
            apiMessageDto.setMessage("Auction not admin approve");
            return apiMessageDto;
        }
        if (updateAuctionForm.getStatus().equals(EAuctionConstant.STATUS_DONE) && !auction.getSeller().getId().equals(admin.getId())) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_BELONG_TO_SELLER);
            apiMessageDto.setMessage("Auction not belong to seller");
            return apiMessageDto;
        }
        auction.setStatus(updateAuctionForm.getStatus());
        auctionRepository.save(auction);
        apiMessageDto.setData(auctionMapper.fromEntityToDto(auction));
        apiMessageDto.setMessage("Update status auction success");
        return apiMessageDto;
    }
}
