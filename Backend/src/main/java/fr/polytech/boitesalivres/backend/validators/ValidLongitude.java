package fr.polytech.boitesalivres.backend.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LongitudeValidator.class)
@Documented
public @interface ValidLongitude {
    String message() default "La longitude doit être comprise entre -180 et 180";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
