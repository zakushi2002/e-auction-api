package com.e.auction.api.view.form.transaction;

import lombok.Data;

@Data
public class CreateTransactionForm {
    private Long auctionId;
    private Long paymentMethodId;
    private Integer duration;
}
