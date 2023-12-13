package com.e.auction.api.view.form.auction;

import com.e.auction.api.view.validation.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateAuctionForm {
    @NotNull(message = "id cannot be null!!!")
    private Long id;
    @Status(message = "status is invalid!!!")
    private Integer status;
}
