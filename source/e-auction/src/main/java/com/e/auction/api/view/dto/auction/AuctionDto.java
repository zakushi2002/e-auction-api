package com.e.auction.api.view.dto.auction;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.product.ProductDto;
import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.dto.bidhistory.BidHistoryDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuctionDto extends InfoAdminDto {
    private ProductDto product;
    private AccountDto seller;
    private AccountDto winner;
    private Integer maxBidders;
    private Double paymentPrice;
    private Double currentPrice;
    private Double minBidPrice;
    private Date startDate;
    private Date endDate;
    private List<BidHistoryDto> bidHistories;
}
