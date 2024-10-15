package com.fitin.shopping.validation;

import com.fitin.shopping.util.PaymentMethod;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPaymentMethodValidator implements ConstraintValidator<ValidPaymentMethod, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return PaymentMethod.isValid(value);
    }
}