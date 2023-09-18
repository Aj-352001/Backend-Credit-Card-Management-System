package com.cardManagement.cardmanagementapp.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cardManagement.cardmanagementapp.entities.BirthDateValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface BirthDate {
    String message() default "Invalid birth date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
