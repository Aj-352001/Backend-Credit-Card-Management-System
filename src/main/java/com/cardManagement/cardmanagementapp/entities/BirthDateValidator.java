package com.cardManagement.cardmanagementapp.entities;

import java.time.LocalDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cardManagement.cardmanagementapp.constraints.BirthDate;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        if (date == null) {
            return true; // Null values are considered valid, you can change this behavior if needed
        }

        LocalDate currentDate = LocalDate.now();
        return date.isBefore(currentDate); // Birthdate should be in the past
    }
}
