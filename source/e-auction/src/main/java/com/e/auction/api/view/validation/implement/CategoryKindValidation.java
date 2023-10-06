package com.e.auction.api.view.validation.implement;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.view.validation.CategoryKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class CategoryKindValidation implements ConstraintValidator<CategoryKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(CategoryKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer categoryKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (categoryKind == null && allowNull) {
            return true;
        }
        return Objects.equals(categoryKind, EAuctionConstant.CATEGORY_KIND_HOSPITAL)
                || Objects.equals(categoryKind, EAuctionConstant.CATEGORY_KIND_HOSPITAL_ROLE)
                || Objects.equals(categoryKind, EAuctionConstant.CATEGORY_KIND_DEPARTMENT)
                || Objects.equals(categoryKind, EAuctionConstant.CATEGORY_KIND_ACADEMIC_DEGREE);
    }
}
