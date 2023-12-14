package com.e.auction.api.view.form.transaction;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateTransactionForm {
    @NotNull(message = "transactionId can not be null!!!")
    private Long transactionId;
    @NotNull(message = "paymentPrice can not be null!!!")
    private Long paymentPrice;
}
