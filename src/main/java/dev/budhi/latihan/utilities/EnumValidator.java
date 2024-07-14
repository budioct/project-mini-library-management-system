package dev.budhi.latihan.utilities;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // or true if null values are allowed
        }
        for (Enum<?> enumConstant : enumClass.getEnumConstants()) {
            if (value.equals(enumConstant)) {
                return true;
            }
        }
        return false;
    }
}
