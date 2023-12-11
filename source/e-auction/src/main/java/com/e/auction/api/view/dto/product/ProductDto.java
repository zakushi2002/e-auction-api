package com.e.auction.api.view.dto.product;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.dto.category.CategoryDto;
import lombok.Data;

@Data
public class ProductDto extends InfoAdminDto {
    private String name;
    private String description;
    private Double startBidPrice;
    private Double buyNowPrice;
    private AccountDto seller;
    private CategoryDto category;
    private Integer bidTime;
    private String mainImage;
    private String subImage1;
    private String subImage2;
    private String subImage3;
}
