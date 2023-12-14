package com.e.auction.api.view.mapper;

import com.e.auction.api.model.Transaction;
import com.e.auction.api.view.dto.transaction.TransactionDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {AuctionMapper.class, PaymentMethodMapper.class, ShippingCostMapper.class})
public interface TransactionMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "auction", target = "auction", qualifiedByName = "fromEntityToDto")
    @Mapping(source = "paymentMethod", target = "paymentMethod", qualifiedByName = "fromEntityToDto")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "shippingCost", target = "shippingCost", qualifiedByName = "fromEntityToDto")
    @Mapping(source = "duration", target = "duration")
    @Mapping(source = "shippingDate", target = "shippingDate")
    @Mapping(source = "receiveDate", target = "receiveDate")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromCreateTransactionFormToDto")
    TransactionDto fromCreateTransactionFormToDto(Transaction transaction);
}
