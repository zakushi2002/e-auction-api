package com.e.auction.api.view.mapper;

import com.e.auction.api.view.dto.account.AccountDto;
import com.e.auction.api.view.form.account.UpdateAdminForm;
import com.e.auction.api.model.Account;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GroupMapper.class})
public interface AccountMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDto")
    AccountDto fromAccountToDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "isSuperAdmin", target = "isSuperAdmin")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToDtoProfile")
    AccountDto fromAccountToDtoProfile(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "kind", target = "kind")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToAutoCompleteDto")
    AccountDto fromAccountToAutoCompleteDto(Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group", qualifiedByName = "fromEntityToGroupDtoAutoComplete")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromAccountToAutoCompleteDtoWithGroup")
    AccountDto fromAccountToAutoCompleteDtoWithGroup(Account account);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToDto")
    List<AccountDto> fromEntityToAccountDtoList(List<Account> accounts);

    @IterableMapping(elementTargetType = AccountDto.class, qualifiedByName = "fromAccountToAutoCompleteDto")
    List<AccountDto> fromEntityToAccountAutoCompleteDtoList(List<Account> accounts);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateAdminFormToAccount(UpdateAdminForm updateAdminForm, @MappingTarget Account account);
}
