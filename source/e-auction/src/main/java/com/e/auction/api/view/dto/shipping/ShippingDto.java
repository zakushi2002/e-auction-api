package com.e.auction.api.view.dto.shipping;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.address.AddressDto;
import lombok.Data;

@Data
public class ShippingDto extends InfoAdminDto {
    private String postalCode;
    private AddressDto province;
    private AddressDto district;
    private AddressDto ward;
    private String address;
    private String phone;
}
