package com.e.auction.api.view.form.account;

import com.e.auction.api.view.validation.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateAdminForm {
    @ApiModelProperty(name = "id", required = true)
    @NotNull(message = "id cannot be null!")
    private Long id;
    @ApiModelProperty(name = "password", required = true)
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "status")
    @Status(allowNull = true)
    private Integer status;
}
