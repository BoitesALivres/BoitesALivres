package fr.polytech.boitesalivres.backend.dtos;

public record UserResponse(
    Long id,
    String firstName,
    String lastName,
    String username,
    String email
) {}
