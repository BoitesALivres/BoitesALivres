package fr.polytech.boitesalivres.backend.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LatitudeValidator implements ConstraintValidator<ValidLatitude, String> {

    @Override
    public boolean isValid(String latitude, ConstraintValidatorContext context) {
        if (latitude == null || latitude.trim().isEmpty()) {
            return false;
        }
        
        try {
            double lat = Double.parseDouble(latitude);
            return lat >= -90.0 && lat <= 90.0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
