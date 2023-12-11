package com.e.auction.api.view.form.bidhistory;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateBidHistoryForm {
    @NotNull(message = "auctionId cannot be null!!!")
    private Long auctionId;
    @NotNull(message = "bidderId cannot be null!!!")
    private Long bidderId;
    @NotNull(message = "bidPrice cannot be null!!!")
    private Double bidPrice;
}
