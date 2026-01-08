package fr.polytech.boitesalivres.backend.dtos;

import fr.polytech.boitesalivres.backend.validators.ValidLatitude;
import fr.polytech.boitesalivres.backend.validators.ValidLongitude;
import jakarta.validation.constraints.*;

public record BoxRequest(
    @NotBlank(message = "Le nom est requis")
    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    String name,
    
    @Size(max = 500, message = "La description ne peut pas dépasser 500 caractères")
    String description,
    
    @NotNull(message = "La quantité est requise")
    @Min(value = 0, message = "La quantité ne peut pas être négative")
    @Max(value = 1000, message = "La quantité ne peut pas dépasser 1000")
    Integer quantity,
    
    @NotBlank(message = "La latitude est requise")
    @ValidLatitude
    String latitude,
    
    @NotBlank(message = "La longitude est requise")
    @ValidLongitude
    String longitude
) {}

