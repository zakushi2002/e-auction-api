package com.e.auction.api.view.validation;

import com.e.auction.api.view.validation.implement.AddressKindValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddressKindValidation.class)
@Documented
public @interface AddressKind {
    boolean allowNull() default false;

    String message() default "Address kind invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
