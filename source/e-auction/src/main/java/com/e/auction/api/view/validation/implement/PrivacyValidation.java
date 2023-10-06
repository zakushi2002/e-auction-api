package com.e.auction.api.view.validation.implement;

import com.e.auction.api.view.validation.Privacy;
import com.e.auction.api.constant.EAuctionConstant;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class PrivacyValidation implements ConstraintValidator<Privacy, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Privacy constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer privacy, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (privacy == null && allowNull) {
            return true;
        }
        return Objects.equals(privacy, EAuctionConstant.PRIVACY_PUBLIC)
                || Objects.equals(privacy, EAuctionConstant.PRIVACY_PRIVATE);
    }
}