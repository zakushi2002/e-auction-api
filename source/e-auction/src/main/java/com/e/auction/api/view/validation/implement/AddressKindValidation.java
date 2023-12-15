package com.e.auction.api.view.validation.implement;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.view.validation.AddressKind;

import javax.validation.ConstraintValidator;
import java.util.Objects;

public class AddressKindValidation implements ConstraintValidator<AddressKind, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(AddressKind constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer addressKind, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        if (addressKind == null && allowNull) {
            return true;
        }
        return Objects.equals(addressKind, EAuctionConstant.ADDRESS_KIND_PROVINCE) ||
                Objects.equals(addressKind, EAuctionConstant.ADDRESS_KIND_DISTRICT) ||
                Objects.equals(addressKind, EAuctionConstant.ADDRESS_KIND_WARD);
    }
}
