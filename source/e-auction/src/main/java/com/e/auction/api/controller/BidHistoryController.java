package com.e.auction.api.controller;

import com.e.auction.api.model.Account;
import com.e.auction.api.model.Auction;
import com.e.auction.api.model.BidHistory;
import com.e.auction.api.model.criteria.BidHistoryCriteria;
import com.e.auction.api.repository.AccountRepository;
import com.e.auction.api.repository.AuctionRepository;
import com.e.auction.api.repository.BidHistoryRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.bidhistory.BidHistoryDto;
import com.e.auction.api.view.form.bidhistory.CreateBidHistoryForm;
import com.e.auction.api.view.mapper.BidHistoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/bid-history")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class BidHistoryController extends BaseController {
    @Autowired
    BidHistoryRepository bidHistoryRepository;
    @Autowired
    BidHistoryMapper bidHistoryMapper;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    AccountRepository accountRepository;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<BidHistoryDto> createBidHistory(CreateBidHistoryForm createBidHistoryForm, BindingResult bindingResult) {
        ApiMessageDto<BidHistoryDto> apiMessageDto = new ApiMessageDto<>();
        Account bidder = accountRepository.findById(createBidHistoryForm.getBidderId()).orElse(null);
        if (bidder == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Bidder not found!!!");
            return apiMessageDto;
        }
        Auction auction = auctionRepository.findById(createBidHistoryForm.getAuctionId()).orElse(null);
        if (auction == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Auction not found!!!");
            return apiMessageDto;
        }
        if (auction.getMinBidPrice() > createBidHistoryForm.getBidPrice()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.BID_HISTORY_ERROR_BID_PRICE_INVALID);
            apiMessageDto.setMessage("Bid price must be greater than min bid price of auction!!!");
            return apiMessageDto;
        }
        BidHistory bidHistory = new BidHistory();
        bidHistory.setBidder(bidder);
        bidHistory.setAuction(auction);
        bidHistory.setBidPrice(createBidHistoryForm.getBidPrice());
        bidHistoryRepository.save(bidHistory);
        apiMessageDto.setData(bidHistoryMapper.fromEntityToDto(bidHistory));
        apiMessageDto.setMessage("You have successfully bid in this auction!!!");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<BidHistoryDto> getBidHistory(@PathVariable("id") Long id) {
        ApiMessageDto<BidHistoryDto> apiMessageDto = new ApiMessageDto<>();
        BidHistory bidHistory = bidHistoryRepository.findById(id).orElse(null);
        if (bidHistory == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.BID_HISTORY_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Bid history not found!!!");
            return apiMessageDto;
        }
        apiMessageDto.setData(bidHistoryMapper.fromEntityToDto(bidHistory));
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<BidHistoryDto>> listBidHistory(BidHistoryCriteria bidHistoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<BidHistoryDto>> apiMessageDto = new ApiMessageDto<>();
        Page<BidHistory> bidHistoryPage = bidHistoryRepository.findAll(bidHistoryCriteria.getSpecification(), pageable);
        ResponseListDto<BidHistoryDto> responseListDto = new ResponseListDto(bidHistoryMapper.fromEntityToDtoList(bidHistoryPage.getContent()), bidHistoryPage.getTotalElements(), bidHistoryPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get list bid history successfully!!!");
        return apiMessageDto;
    }
}
