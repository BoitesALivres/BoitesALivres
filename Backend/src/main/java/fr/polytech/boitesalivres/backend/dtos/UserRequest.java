package fr.polytech.boitesalivres.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotBlank String username,
    @NotBlank @Email String email,
    @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*\\W).{8,}$") String password
) {}
