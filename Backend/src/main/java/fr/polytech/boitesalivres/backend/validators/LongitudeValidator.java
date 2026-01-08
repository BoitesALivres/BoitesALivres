package fr.polytech.boitesalivres.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LongitudeValidator implements ConstraintValidator<ValidLongitude, String> {

    @Override
    public boolean isValid(String longitude, ConstraintValidatorContext context) {
        if (longitude == null || longitude.trim().isEmpty()) {
            return false;
        }
        
        try {
            double lon = Double.parseDouble(longitude);
            return lon >= -180.0 && lon <= 180.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
