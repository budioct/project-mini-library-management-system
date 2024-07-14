package dev.budhi.latihan.utilities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return false; // NotBlank will handle the empty check
        }
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (enumConstant.name().equals(value)) {
                return true;
            }
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                "Invalid role. Valid values: " + Arrays.toString(enumClass.getEnumConstants())
        ).addConstraintViolation();

        return false;
    }
}

