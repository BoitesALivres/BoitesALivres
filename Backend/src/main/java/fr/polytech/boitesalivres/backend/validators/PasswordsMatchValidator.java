package fr.polytech.boitesalivres.backend.validators;

import fr.polytech.boitesalivres.backend.dtos.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordsMatchValidator implements ConstraintValidator<PasswordsMatch, RegisterRequest> {
    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value.password().equals(value.password_confirmation());
    }
}
