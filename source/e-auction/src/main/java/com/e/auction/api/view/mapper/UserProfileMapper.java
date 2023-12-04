package com.e.auction.api.view.mapper;

import com.e.auction.api.model.UserProfile;
import com.e.auction.api.view.dto.profile.user.UserProfileDto;
import com.e.auction.api.view.form.profile.user.CreateUserAccountForm;
import com.e.auction.api.view.form.profile.user.UpdateUserAccountForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
public interface UserProfileMapper {
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "gender", target = "gender")
    @BeanMapping(ignoreByDefault = true)
    UserProfile fromCreateUserAccountFormToEntity(CreateUserAccountForm createUserAccountForm);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "kind")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.fullName", target = "fullName")
    @Mapping(source = "account.avatarPath", target = "avatarPath")
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "shipping.id", target = "shippingId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForClient")
    UserProfileDto fromEntityToDtoForClient(UserProfile userProfile);

    @IterableMapping(elementTargetType = UserProfileDto.class, qualifiedByName = "fromEntityToDtoForClient")
    @Named("fromEntityListToDtoListForClient")
    List<UserProfileDto> fromEntityListToDtoListForClient(List<UserProfile> userProfileList);

    @Mapping(source = "account.id", target = "id")
    @Mapping(source = "account.kind", target = "kind")
    @Mapping(source = "account.email", target = "email")
    @Mapping(source = "account.fullName", target = "fullName")
    @Mapping(source = "account.avatarPath", target = "avatarPath")
    @Mapping(source = "account.group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "shipping.id", target = "shippingId")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoForServer")
    UserProfileDto fromEntityToDtoForServer(UserProfile userProfile);

    @IterableMapping(elementTargetType = UserProfileDto.class, qualifiedByName = "fromEntityToDtoForServer")
    @Named("fromEntityListToDtoListForServer")
    List<UserProfileDto> fromEntityListToDtoListForServer(List<UserProfile> userProfileList);

    @Mapping(source = "fullName", target = "account.fullName")
    @Mapping(source = "birthdate", target = "birthdate")
    @Mapping(source = "gender", target = "gender")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateUserAccountFormToEntity(UpdateUserAccountForm updateUserAccountForm, @MappingTarget UserProfile userProfile);
}
