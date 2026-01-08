package fr.polytech.boitesalivres.backend.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullableNotBlankValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableNotBlank {
    String message() default "Field cannot be blank if provided";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
