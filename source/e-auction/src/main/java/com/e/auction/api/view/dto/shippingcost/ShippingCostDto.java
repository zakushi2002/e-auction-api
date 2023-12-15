package com.e.auction.api.view.dto.shippingcost;

import com.e.auction.api.view.dto.InfoAdminDto;
import lombok.Data;

@Data
public class ShippingCostDto extends InfoAdminDto {
    private String sellerPostalCode;
    private String buyerPostalCode;
}
