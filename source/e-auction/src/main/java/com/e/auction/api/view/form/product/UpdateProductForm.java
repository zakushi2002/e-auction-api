package com.e.auction.api.view.form.product;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UpdateProductForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id can not be null!")
    private Long id;
    @ApiModelProperty(name = "name", required = true)
    @NotEmpty(message = "name can not be empty!")
    private String name;
    private String description;
    private Double startBidPrice;
    private Double buyNowPrice;
    private Long categoryId;
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
