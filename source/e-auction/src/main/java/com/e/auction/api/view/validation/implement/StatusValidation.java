package com.e.auction.api.view.validation.implement;

import com.e.auction.api.constant.EAuctionConstant;
import com.e.auction.api.view.validation.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class StatusValidation implements ConstraintValidator<Status, Integer> {
    private boolean allowNull;

    @Override
    public void initialize(Status constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(Integer status, ConstraintValidatorContext constraintValidatorContext) {
        if (status == null && allowNull) {
            return true;
        }
        return Objects.equals(status, EAuctionConstant.STATUS_PENDING)
                || Objects.equals(status, EAuctionConstant.STATUS_ACTIVE)
                || Objects.equals(status, EAuctionConstant.STATUS_LOCK)
                || Objects.equals(status, EAuctionConstant.STATUS_DELETE);
    }
}
