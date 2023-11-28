package com.e.auction.api.view.validation.implement;

import com.e.auction.api.view.validation.Gender;
import com.e.auction.api.constant.EAuctionConstant;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class GenderValidation implements ConstraintValidator<Gender, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Gender constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer gender, ConstraintValidatorContext constraintValidatorContext) {
        if (gender == null && allowNull) {
            return true;
        }
        return Objects.equals(gender, EAuctionConstant.GENDER_MALE)
                || Objects.equals(gender, EAuctionConstant.GENDER_FEMALE);
    }
}
