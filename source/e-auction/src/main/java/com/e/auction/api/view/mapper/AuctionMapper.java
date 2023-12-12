package com.e.auction.api.view.mapper;

import com.e.auction.api.model.Auction;
import com.e.auction.api.view.dto.auction.AuctionDto;
import com.e.auction.api.view.form.auction.CreateAuctionForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, ProductMapper.class})
public interface AuctionMapper {
    @Mapping(source = "maxBidders", target = "maxBidders")
    @Mapping(source = "paymentPrice", target = "paymentPrice")
    @Mapping(source = "minBidPrice", target = "minBidPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    Auction fromCreateFormToModel(CreateAuctionForm createAuctionForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "product", target = "product", qualifiedByName = "fromEntityToDtoAutoComplete")
    @Mapping(source = "seller", target = "seller", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "maxBidders", target = "maxBidders")
    @Mapping(source = "paymentPrice", target = "paymentPrice")
    @Mapping(source = "minBidPrice", target = "minBidPrice")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "winner", target = "winner")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    AuctionDto fromEntityToDto(Auction auction);

    @IterableMapping(elementTargetType = AuctionDto.class, qualifiedByName = "fromEntityToDto")
    List<AuctionDto> fromEntityToDtoList(List<Auction> auctions);
}
