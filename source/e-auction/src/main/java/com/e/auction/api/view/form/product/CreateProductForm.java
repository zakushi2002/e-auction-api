package com.e.auction.api.view.form.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
@Data
@ApiModel
public class CreateProductForm {
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name can not be empty!")
    private String name;
    @ApiModelProperty(name = "description", required = true)
    @NotEmpty(message = "description can not be empty!")
    private String description;
    @ApiModelProperty(name = "startBidPrice", required = true)
    @NotNull(message = "startBidPrice can not be null!")
    private Double startBidPrice;
    @ApiModelProperty(name = "buyNowPrice")
    private Double buyNowPrice;
    @ApiModelProperty(name = "categoryId")
    private Long categoryId;
    @ApiModelProperty(name = "bidTime", required = true)
    @NotNull(message = "bidTime can not be null!")
    private Integer bidTime;
    @ApiModelProperty(name = "mainImage", required = true)
    @NotEmpty(message = "mainImage can not be empty!")
    private String mainImage;
    @ApiModelProperty(name = "subImage1")
    private String subImage1;
    @ApiModelProperty(name = "subImage2")
    private String subImage2;
    @ApiModelProperty(name = "subImage3")
    private String subImage3;
    @ApiModelProperty(name = "status", required = true)
    @NotNull(message = "status can not be null!")
    private Integer status;
}
