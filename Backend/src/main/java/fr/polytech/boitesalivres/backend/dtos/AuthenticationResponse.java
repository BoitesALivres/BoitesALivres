package fr.polytech.boitesalivres.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthenticationResponse(
        @NotBlank(message = "L'identifiant ne doit pas être vide") @NotNull(
                message = "L'identifiant est obligatoire") String id,

        @NotBlank(message = "Le prénom ne doit pas être vide") @NotNull(
                message = "Le prénom est obligatoire") String firstName,

        @NotBlank(message = "Le nom ne doit pas être vide") @NotNull(
                message = "Le nom est obligatoire") String lastName,

        @NotBlank(message = "Le nom d'utilisateur ne doit pas être vide") @NotNull(
                message = "Le nom d'utilisateur est obligatoire") String username,

        @NotBlank(message = "L'email ne doit pas être vide") @NotNull(
                message = "L'email est obligatoire") @Email(
                        message = "L'email doit être valide") String email) {
}
