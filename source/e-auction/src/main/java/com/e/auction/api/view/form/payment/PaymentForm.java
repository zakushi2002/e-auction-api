package com.e.auction.api.view.form.payment;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class PaymentForm {
    @NotNull(message = "transactionId can not be null!!!")
    private Long transactionId;
    @NotNull(message = "paymentPrice can not be null!!!")
    private Long paymentPrice;
    @NotEmpty(message = "bankCode can not be empty!!!")
    private String bankCode;
}
