package com.e.auction.api.view.dto.paymentmethod;

import com.e.auction.api.view.dto.InfoAdminDto;
import lombok.Data;

@Data
public class PaymentMethodDto extends InfoAdminDto {
    private String name;
}
