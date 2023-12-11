package com.e.auction.api.controller;

import com.e.auction.api.model.Account;
import com.e.auction.api.model.Category;
import com.e.auction.api.model.Product;
import com.e.auction.api.model.criteria.ProductCriteria;
import com.e.auction.api.repository.AccountRepository;
import com.e.auction.api.repository.CategoryRepository;
import com.e.auction.api.repository.ProductRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.product.ProductDto;
import com.e.auction.api.view.form.product.CreateProductForm;
import com.e.auction.api.view.form.product.ListProductBySellerAndStatusForm;
import com.e.auction.api.view.form.product.UpdateProductForm;
import com.e.auction.api.view.mapper.ProductMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/product")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class ProductController extends BaseController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductMapper productMapper;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRODUCT_CREATE')")
    @Transactional
    public ApiMessageDto<ProductDto> createProduct(@Valid @RequestBody CreateProductForm createProductForm, BindingResult bindingResult) {
        ApiMessageDto<ProductDto> apiMessageDto = new ApiMessageDto<>();
        Product product = productMapper.fromCreateProductFormToEntity(createProductForm);
        if (createProductForm.getCategoryId() != null) {
            Category category = categoryRepository.findById(createProductForm.getCategoryId()).orElse(null);
            if (category == null) {
                apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
                apiMessageDto.setResult(false);
                return apiMessageDto;
            }
            product.setCategory(category);
        }
        Account seller = accountRepository.findById(getCurrentUser()).orElse(null);
        if (seller == null) {
            apiMessageDto.setCode(ErrorCode.ACCOUNT_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        product.setSeller(seller);
        productRepository.save(product);
        apiMessageDto.setData(productMapper.fromEntityToDto(product));
        return apiMessageDto;
    }

    @GetMapping(value = "/detail/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ProductDto> detailProduct(@PathVariable Long id) {
        ApiMessageDto<ProductDto> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            apiMessageDto.setCode(ErrorCode.PRODUCT_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        apiMessageDto.setData(productMapper.fromEntityToDto(product));
        apiMessageDto.setMessage("Detail product success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProductDto>> listProduct(ProductCriteria productCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProductDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Product> productPage = productRepository.findAll(productCriteria.getSpecification(), pageable);
        ResponseListDto<ProductDto> responseListDto = new ResponseListDto(productMapper.fromEntityListToDtoList(productPage.getContent()), productPage.getTotalElements(), productPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List product success");
        return apiMessageDto;
    }

    @GetMapping(value = "/list-by-seller", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<ProductDto>> listProductBySeller(ListProductBySellerAndStatusForm listProductBySellerAndStatusForm, Pageable pageable) {
        ApiMessageDto<ResponseListDto<ProductDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Product> productPage = productRepository.findAllBySellerIdAndStatus(listProductBySellerAndStatusForm.getSellerId(), listProductBySellerAndStatusForm.getStatus(), pageable);
        ResponseListDto<ProductDto> responseListDto = new ResponseListDto(productMapper.fromEntityListToDtoList(productPage.getContent()), productPage.getTotalElements(), productPage.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("List product by seller success");
        return apiMessageDto;
    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRODUCT_UPDATE')")
    @Transactional
    public ApiMessageDto<ProductDto> updateProduct(@Valid @RequestBody UpdateProductForm updateProductForm, BindingResult bindingResult) {
        ApiMessageDto<ProductDto> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(updateProductForm.getId()).orElse(null);
        if (product == null) {
            apiMessageDto.setCode(ErrorCode.PRODUCT_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        if (updateProductForm.getCategoryId() != null) {
            Category category = categoryRepository.findById(updateProductForm.getCategoryId()).orElse(null);
            if (category == null) {
                apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
                apiMessageDto.setResult(false);
                apiMessageDto.setMessage("Category not found");
                return apiMessageDto;
            }
            product.setCategory(category);
        }
        productMapper.mappingUpdateProductFormToEntity(updateProductForm, product);
        productRepository.save(product);
        apiMessageDto.setData(productMapper.fromEntityToDto(product));
        apiMessageDto.setMessage("Update product success");
        return apiMessageDto;
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('PRODUCT_DELETE')")
    @Transactional
    public ApiMessageDto<Long> deleteProduct(@PathVariable Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            apiMessageDto.setCode(ErrorCode.PRODUCT_ERROR_NOT_FOUND);
            apiMessageDto.setResult(false);
            return apiMessageDto;
        }
        productRepository.deleteById(id);
        apiMessageDto.setData(id);
        apiMessageDto.setMessage("Delete product success");
        return apiMessageDto;
    }
}
