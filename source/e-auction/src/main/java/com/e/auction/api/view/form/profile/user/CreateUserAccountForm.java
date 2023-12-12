package com.e.auction.api.view.form.profile.user;

import com.e.auction.api.view.validation.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class CreateUserAccountForm {
    @Email
    @NotEmpty(message = "email cannot be empty!")
    @ApiModelProperty(name = "email", required = true)
    private String email;
    @NotEmpty(message = "password cannot be empty!")
    @ApiModelProperty(name = "password", required = true)
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;
    @ApiModelProperty(name = "fullName", required = true)
    @NotEmpty(message = "fullName cannot be empty!")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "dateOfBirth", required = true)
    /*@NotNull(message = "birthdate cannot be null!")
    @Past(message = "birthdate must be in the past!")*/
    private Date birthdate;
    @ApiModelProperty(name = "gender")
    @Gender(message = "Gender must be 1 (Male) or 2 (Female)!", allowNull = true)
    private Integer gender;
}
