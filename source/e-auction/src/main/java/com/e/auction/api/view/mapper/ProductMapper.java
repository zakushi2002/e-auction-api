package com.e.auction.api.view.mapper;

import com.e.auction.api.model.Product;
import com.e.auction.api.view.dto.product.ProductDto;
import com.e.auction.api.view.form.product.CreateProductForm;
import com.e.auction.api.view.form.product.UpdateProductForm;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, CategoryMapper.class})
public interface ProductMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startBidPrice", target = "startBidPrice")
    @Mapping(source = "buyNowPrice", target = "buyNowPrice")
    @Mapping(source = "bidTime", target = "bidTime")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(source = "subImage1", target = "subImage1")
    @Mapping(source = "subImage2", target = "subImage2")
    @Mapping(source = "subImage3", target = "subImage3")
    @BeanMapping(ignoreByDefault = true)
    Product fromCreateProductFormToEntity(CreateProductForm createProductForm);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startBidPrice", target = "startBidPrice")
    @Mapping(source = "buyNowPrice", target = "buyNowPrice")
    @Mapping(source = "bidTime", target = "bidTime")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(source = "subImage1", target = "subImage1")
    @Mapping(source = "subImage2", target = "subImage2")
    @Mapping(source = "subImage3", target = "subImage3")
    @Mapping(source = "seller", target = "seller", qualifiedByName = "fromAccountToAutoCompleteDto")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDto")
    ProductDto fromEntityToDto(Product product);

    @IterableMapping(elementTargetType = ProductDto.class, qualifiedByName = "fromEntityToDto")
    List<ProductDto> fromEntityListToDtoList(List<Product> products);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startBidPrice", target = "startBidPrice")
    @Mapping(source = "buyNowPrice", target = "buyNowPrice")
    @Mapping(source = "bidTime", target = "bidTime")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(source = "subImage1", target = "subImage1")
    @Mapping(source = "subImage2", target = "subImage2")
    @Mapping(source = "subImage3", target = "subImage3")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    void mappingUpdateProductFormToEntity(UpdateProductForm updateProductForm, @MappingTarget Product product);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "startBidPrice", target = "startBidPrice")
    @Mapping(source = "buyNowPrice", target = "buyNowPrice")
    @Mapping(source = "bidTime", target = "bidTime")
    @Mapping(source = "mainImage", target = "mainImage")
    @Mapping(source = "subImage1", target = "subImage1")
    @Mapping(source = "subImage2", target = "subImage2")
    @Mapping(source = "subImage3", target = "subImage3")
    @Mapping(source = "category", target = "category", qualifiedByName = "fromEntityToShortDto")
    @Mapping(source = "status", target = "status")
    @BeanMapping(ignoreByDefault = true)
    @Named("fromEntityToDtoAutoComplete")
    ProductDto fromEntityToDtoAutoComplete(Product product);
}
