package com.e.auction.api.view.validation;

import com.e.auction.api.view.validation.implement.GenderValidation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GenderValidation.class)
@Documented
public @interface Gender {
    boolean allowNull() default false;

    String message() default "Gender invalid!";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
