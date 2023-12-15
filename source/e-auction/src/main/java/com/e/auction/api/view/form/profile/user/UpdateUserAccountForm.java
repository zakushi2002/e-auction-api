package com.e.auction.api.view.form.profile.user;

import com.e.auction.api.view.validation.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class UpdateUserAccountForm {
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "birthdate")
    @Past(message = "birthdate must be in the past!")
    private Date birthdate;
    @ApiModelProperty(name = "gender")
    @Gender(message = "Gender must be 1 (Male) or 2 (Female)!", allowNull = true)
    private Integer gender;
    @ApiModelProperty(name = "shippingId")
    private Long shippingId;
}
