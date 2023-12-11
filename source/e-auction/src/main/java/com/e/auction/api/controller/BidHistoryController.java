package com.e.auction.api.controller;

import com.e.auction.api.model.BidHistory;
import com.e.auction.api.repository.AuctionRepository;
import com.e.auction.api.repository.BidHistoryRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.bidhistory.BidHistoryDto;
import com.e.auction.api.view.form.bidhistory.CreateBidHistoryForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bid-history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BidHistoryController extends BaseController {
    @Autowired
    BidHistoryRepository bidHistoryRepository;
    @Autowired
    AuctionRepository auctionRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<BidHistoryDto> createBidHistory(CreateBidHistoryForm createBidHistoryForm, BindingResult bindingResult) {
        ApiMessageDto<BidHistoryDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Create bid history success");
        return apiMessageDto;
    }
}
