package fr.polytech.boitesalivres.backend.dtos;

import fr.polytech.boitesalivres.backend.validators.PasswordsMatch;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@PasswordsMatch(message = "Les champs mot de passe et confirmation du mot de passe ne correspondent pas")
public record RegisterRequest(
                @NotBlank(message = "Le nom d'utilisateur est requis") String username,

                @NotBlank(message = "Le prénom est requis") String first_name,

                @NotBlank(message = "Le nom de famille est requis") String last_name,

                @NotBlank(message = "L'email est requis") @Email(
                                message = "Format d'email invalide") String email,

                @NotBlank(message = "Le mot de passe est requis") @Pattern(
                                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
                                message = "Le mot de passe doit faire au moins 8 caractères et contenir une minuscule, une majuscule, un chiffre et un caractère spécial.") String password,

                @NotBlank(message = "La confirmation du mot de passe est requise") @Pattern(
                                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_]).{8,}$",
                                message = "La confirmation du mot de passe doit faire au moins 8 caractères et contenir une minuscule, une majuscule, un chiffre et un caractère spécial.") String password_confirmation) {
}
