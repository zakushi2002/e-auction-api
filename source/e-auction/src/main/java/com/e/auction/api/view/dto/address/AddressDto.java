package com.e.auction.api.view.dto.address;

import com.e.auction.api.view.dto.InfoAdminDto;
import lombok.Data;

import java.util.List;

@Data
public class AddressDto extends InfoAdminDto {
    private String addressId;
    private String name;
    private String postalCode;
    private Integer kind;
    private AddressDto parent;
    private List<AddressDto> children;
}
