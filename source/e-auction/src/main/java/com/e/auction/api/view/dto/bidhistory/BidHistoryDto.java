package com.e.auction.api.view.dto.bidhistory;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.dto.auction.AuctionDto;
import lombok.Data;

@Data
public class BidHistoryDto extends InfoAdminDto {
    private AccountDto bidder;
    private AuctionDto auction;
    private Double bidPrice;
}
