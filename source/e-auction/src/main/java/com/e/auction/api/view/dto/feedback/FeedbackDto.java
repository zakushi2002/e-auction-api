package com.e.auction.api.view.dto.feedback;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.product.ProductDto;
import com.e.auction.api.view.dto.account.AccountDto;
import lombok.Data;

@Data
public class FeedbackDto extends InfoAdminDto {
    private ProductDto product;
    private AccountDto buyer;
    private String content;
    private Integer kind;
}
