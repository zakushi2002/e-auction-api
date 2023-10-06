package com.e.auction.api.view.dto.account;

import com.e.auction.api.view.dto.InfoAdminDto;
import com.e.auction.api.view.dto.group.GroupDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel
public class AccountDto extends InfoAdminDto {
    @ApiModelProperty(name = "kind")
    private Integer kind;
    @ApiModelProperty(name = "email")
    private String email;
    @ApiModelProperty(name = "fullName")
    private String fullName;
    @ApiModelProperty(name = "avatar")
    private String avatar;
    @ApiModelProperty(name = "isSuperAdmin")
    private Boolean isSuperAdmin;
    @ApiModelProperty(name = "group")
    private GroupDto group;
    @ApiModelProperty(name = "lastLogin")
    private Date lastLogin;
}
