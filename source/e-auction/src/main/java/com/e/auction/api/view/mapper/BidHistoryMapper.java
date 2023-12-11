package com.e.auction.api.view.mapper;

import com.e.auction.api.model.BidHistory;
import com.e.auction.api.view.dto.bidhistory.BidHistoryDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, AuctionMapper.class})
public interface BidHistoryMapper {
    @Mapping(source = "bidder", target = "bidder", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "auction", target = "auction", qualifiedByName = "fromEntityToDto")
    @Mapping(source = "bidPrice", target = "bidPrice")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    BidHistoryDto fromEntityToDto(BidHistory bidHistory);

    @IterableMapping(elementTargetType = BidHistoryDto.class, qualifiedByName = "fromEntityToDto")
    @Named("fromEntityToDtoList")
    List<BidHistoryDto> fromEntityToDtoList(List<BidHistory> bidHistories);
}
