package com.e.auction.api.view.dto.profile.user;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.group.GroupDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserProfileDto extends InfoAdminDto {
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "avatarPath")
    private String avatarPath;
    @ApiModelProperty(name = "group")
    private GroupDto group;
    @ApiModelProperty(name = "birthdate")
    private Date birthdate;
    @ApiModelProperty(name = "gender")
    private Integer gender;
    @ApiModelProperty(name = "shippingId")
    private Long shippingId;
}
