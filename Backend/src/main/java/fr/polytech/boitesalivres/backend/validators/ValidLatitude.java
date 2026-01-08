package fr.polytech.boitesalivres.backend.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LatitudeValidator.class)
@Documented
public @interface ValidLatitude {
    String message() default "La latitude doit être comprise entre -90 et 90";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
