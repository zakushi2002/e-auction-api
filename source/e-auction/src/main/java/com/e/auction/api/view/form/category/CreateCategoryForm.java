package com.e.auction.api.view.form.category;

import com.e.auction.api.view.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryForm {
    @ApiModelProperty(name = "categoryName", required = true)
    @NotEmpty(message = "categoryName cannot be null")
    private String categoryName;
    @ApiModelProperty(name = "categoryDescription", required = true)
    @NotEmpty(message = "categoryDescription cannot be null")
    private String categoryDescription;
    @ApiModelProperty(name = "categoryImage")
    private String categoryImage;
    @ApiModelProperty(name = "categoryOrdering")
    private Integer categoryOrdering;
    @ApiModelProperty(name = "categoryKind", required = true)
    @NotNull(message = "categoryKind cannot be null")
    private Integer categoryKind;
    @ApiModelProperty(name = "parentId")
    private Long parentId;
    @ApiModelProperty(name = "status", required = true)
    @NotNull(message = "status can not be null")
    @Status
    private Integer status;
}
