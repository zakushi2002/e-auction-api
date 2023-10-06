package com.e.auction.api.controller;

import com.e.auction.api.repository.CategoryRepository;
import com.e.auction.api.view.dto.ApiMessageDto;
import com.e.auction.api.view.dto.ErrorCode;
import com.e.auction.api.view.dto.ResponseListDto;
import com.e.auction.api.view.dto.category.CategoryDto;
import com.e.auction.api.view.form.category.CreateCategoryForm;
import com.e.auction.api.view.form.category.UpdateCategoryForm;
import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.model.Category;
import com.e.auction.api.model.criteria.CategoryCriteria;
import com.e.auction.api.service.EAuctionApiService;
import com.e.auction.api.view.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/v1/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CategoryController extends BaseController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    EAuctionApiService apiService;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_L')")
    public ApiMessageDto<ResponseListDto<CategoryDto>> listCategory(CategoryCriteria categoryCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CategoryDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);
        ResponseListDto<CategoryDto> responseListObj = new ResponseListDto(categoryMapper.fromEntityListToCategoryDtoList(listCategory.getContent()), listCategory.getTotalElements(), listCategory.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get list category success");
        return apiMessageDto;
    }

    @GetMapping(value = "/auto-complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CategoryDto>> autoCompleteListCategory(CategoryCriteria categoryCriteria, @PageableDefault(size = 10) Pageable pageable) {
        ApiMessageDto<ResponseListDto<CategoryDto>> apiMessageDto = new ApiMessageDto<>();
        categoryCriteria.setStatus(EAuctionConstant.STATUS_ACTIVE);
        Page<Category> listCategory = categoryRepository.findAll(categoryCriteria.getSpecification(), pageable);
        ResponseListDto<CategoryDto> responseListObj = new ResponseListDto(categoryMapper.fromEntityListToCategoryDtoAutoComplete(listCategory.getContent()), listCategory.getTotalElements(), listCategory.getTotalPages());
        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("Get auto-complete list category success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_V')")
    public ApiMessageDto<CategoryDto> get(@PathVariable("id") Long id) {
        ApiMessageDto<CategoryDto> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Not found category");
            return apiMessageDto;
        }
        apiMessageDto.setData(categoryMapper.fromEntityToAdminDto(category));
        apiMessageDto.setMessage("Get category success");
        return apiMessageDto;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_C')")
    @Transactional
    public ApiMessageDto<Long> createCategory(@Valid @RequestBody CreateCategoryForm createCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findByNameAndKind(createCategoryForm.getCategoryName(), createCategoryForm.getCategoryKind()).orElse(null);
        if (category != null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NAME_EXIST_IN_KIND);
            apiMessageDto.setMessage("Category name is exist");
            return apiMessageDto;
        }
        category = categoryMapper.fromCreateCategoryFormToEntity(createCategoryForm);
        if (createCategoryForm.getParentId() != null) {
            Category parentCategory = categoryRepository.findById(createCategoryForm.getParentId()).orElse(null);
            if (parentCategory == null || parentCategory.getParentCategory() != null) {
                apiMessageDto.setResult(false);
                apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
                apiMessageDto.setMessage("Not found parent category or parent category is child of other category.");
                return apiMessageDto;
            }
            category.setParentCategory(parentCategory);
        }
        categoryRepository.save(category);
        apiMessageDto.setData(category.getId());
        apiMessageDto.setMessage("Create category success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CATE_U')")
    @Transactional
    public ApiMessageDto<Long> updateCategory(@Valid @RequestBody UpdateCategoryForm updateCategoryForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(updateCategoryForm.getId()).orElse(null);
        if (category == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Not found category.");
            return apiMessageDto;
        }
        Category checkName = categoryRepository.findByNameAndKind(updateCategoryForm.getCategoryName(), category.getKind()).orElse(null);
        if (checkName == null && !Objects.equals(category.getName(), updateCategoryForm.getCategoryName().trim())) {
            category.setName(updateCategoryForm.getCategoryName().trim());
        }
        if (Objects.equals(Objects.requireNonNull(category).getStatus(), updateCategoryForm.getStatus()) && category.getParentCategory() == null && !category.getCategoryList().isEmpty()) {
            category.getCategoryList().forEach(child -> child.setStatus(updateCategoryForm.getStatus()));
            categoryRepository.saveAll(category.getCategoryList());
        }
        if (StringUtils.isNoneBlank(category.getImage()) && !updateCategoryForm.getCategoryImage().equals(category.getImage())) {
            category.setImage(updateCategoryForm.getCategoryImage());
        }
        categoryMapper.fromUpdateCategoryFormToEntity(updateCategoryForm, category);
        categoryRepository.save(category);
        apiMessageDto.setData(category.getId());
        apiMessageDto.setMessage("Update category success");
        return apiMessageDto;
    }

    @PreAuthorize("hasRole('CATE_D')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> deleteCategory(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.CATEGORY_ERROR_NOT_FOUND);
            apiMessageDto.setMessage("Not found category");
            return apiMessageDto;
        }
        categoryRepository.deleteById(id);
        apiMessageDto.setData(id);
        apiMessageDto.setMessage("Delete category success");
        return apiMessageDto;
    }
}
