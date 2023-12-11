package com.e.auction.api.view.form.product;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ListProductBySellerAndStatusForm {
    @NotNull(message = "sellerId can not be null!!!")
    private Long sellerId;
    @NotNull(message = "status can not be null!!!")
    private Integer status;
}
