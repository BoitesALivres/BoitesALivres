package fr.polytech.boitesalivres.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
                @Email(message = "Format d'email invalide") @NotBlank(
                                message = "L'email est requis") String email,

                @NotBlank(message = "Le mot de passe est requis") @Pattern(
                                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
                                message = "Le mot de passe doit faire au moins 8 caractères et contenir une minuscule, une majuscule, un chiffre et un caractère spécial.") String password) {
}
