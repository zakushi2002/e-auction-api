package com.e.auction.api.view.mapper;

import com.e.auction.api.model.Address;
import com.e.auction.api.view.dto.address.AddressDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
    @Mapping(source = "id", target = "addressId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "postalCode", target = "postalCode")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    AddressDto fromEntityToDto(Address address);

    @Mapping(source = "id", target = "addressId")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToAutoCompleteDto")
    AddressDto fromEntityToAutoCompleteDto(Address address);


    @IterableMapping(elementTargetType = AddressDto.class, qualifiedByName = "fromEntityToDto")
    @Named("fromEntityListToDtoList")
    List<AddressDto> fromEntityListToDtoList(List<Address> addresses);

    @Mapping(source = "id", target = "addressId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "postalCode", target = "postalCode")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "parent", target = "parent", qualifiedByName = "fromEntityToAutoCompleteDto")
    @Mapping(source = "childList", target = "children", qualifiedByName = "fromEntityListToDtoList")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoWithChildren")
    AddressDto fromEntityToDtoWithChildren(Address address);
}
