package com.e.auction.api.controller;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.model.*;
import com.e.auction.api.repository.*;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.transaction.TransactionDto;
import com.e.auction.api.view.form.transaction.CreateTransactionForm;
import com.e.auction.api.view.mapper.TransactionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/v1/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class TransactionController extends BaseController {
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AuctionRepository auctionRepository;
    @Autowired
    PaymentMethodRepository paymentMethodRepository;
    @Autowired
    ShippingCostRepository shippingCostRepository;
    @Autowired
    TransactionMapper transactionMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    // @PreAuthorize("hasRole('TRANSACTION_CREATE')")
    public ApiMessageDto<TransactionDto> createTransaction(@Valid @RequestBody CreateTransactionForm createTransactionForm) {
        ApiMessageDto<TransactionDto> apiMessageDto = new ApiMessageDto<>();
        Auction auction = auctionRepository.findById(createTransactionForm.getAuctionId()).orElse(null);
        if (auction == null) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Auction not found");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (!auction.getWinner().getId().equals(getCurrentUser())) {
            apiMessageDto.setCode(ErrorCode.TRANSACTION_ERROR_NOT_BELONG_TO_WINNER);
            apiMessageDto.setMessage("Auction not belong to winner");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (!auction.getStatus().equals(EAuctionConstant.STATUS_DONE)) {
            apiMessageDto.setCode(ErrorCode.AUCTION_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Auction not done");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        Account bidder = accountRepository.findById(getCurrentUser()).orElse(null);
        if (bidder == null) {
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        PaymentMethod paymentMethod =  paymentMethodRepository.findById(createTransactionForm.getPaymentMethodId()).orElse(null);
        if (paymentMethod == null) {
            apiMessageDto.setCode(ErrorCode.PAYMENT_METHOD_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Payment method not found");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        ShippingCost shippingCost = shippingCostRepository.findById(1L).orElse(null);
        if (shippingCost == null) {
            apiMessageDto.setCode(ErrorCode.SHIPPING_COST_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Shipping cost not found");
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        Transaction transaction = new Transaction();
        transaction.setBidder(bidder);
        transaction.setPaymentMethod(paymentMethod);
        transaction.setAuction(auction);
        transaction.setAddress(createTransactionForm.getAddress());
        transaction.setShippingCost(shippingCost);
        transaction.setDuration(7);
        transaction.setShippingDate(DateUtils.addDays(new Date(), 7));
        transaction.setStatus(EAuctionConstant.STATUS_PENDING);
        transactionRepository.save(transaction);
        auction.setStatus(EAuctionConstant.STATUS_TRANSACTION_PENDING);
        auctionRepository.save(auction);
        apiMessageDto.setData(transactionMapper.fromCreateTransactionFormToDto(transaction));
        apiMessageDto.setMessage("Create transaction success");
        return apiMessageDto;
    }
}
