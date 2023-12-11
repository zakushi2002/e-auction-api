package com.e.auction.api.view.form.auction;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateAuctionForm {
    @NotNull(message = "productId can not be null!!!")
    private Long productId;
    @NotNull(message = "sellerId can not be null!!!")
    private Long sellerId;
    @NotNull(message = "maxBidders can not be null!!!")
    private Integer maxBidders;
    private Double paymentPrice;
    @NotNull(message = "minBidPrice can not be null!!!")
    private Double minBidPrice;
    private Date startDate;
    private Date endDate;
}
