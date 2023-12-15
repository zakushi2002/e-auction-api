package com.e.auction.api.view.mapper;

import com.e.auction.api.model.ShippingCost;
import com.e.auction.api.view.dto.shippingcost.ShippingCostDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShippingCostMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "sellerPostalCode", target = "sellerPostalCode")
    @Mapping(source = "buyerPostalCode", target = "buyerPostalCode")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    ShippingCostDto fromEntityToDto(ShippingCost shippingCost);
}
