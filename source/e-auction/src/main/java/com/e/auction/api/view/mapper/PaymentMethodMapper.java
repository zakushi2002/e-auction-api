package com.e.auction.api.view.mapper;

import com.e.auction.api.model.PaymentMethod;
import com.e.auction.api.view.dto.paymentmethod.PaymentMethodDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMethodMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    PaymentMethodDto fromEntityToDto(PaymentMethod paymentMethod);
}
