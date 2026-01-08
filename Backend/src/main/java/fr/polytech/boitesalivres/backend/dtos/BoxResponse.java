package fr.polytech.boitesalivres.backend.dtos;

import jakarta.annotation.Nullable;

public record BoxResponse(
    Long id,
    String name,
    String description,
    Integer quantity,
    @Nullable Long coordinateId,
    @Nullable String latitude,
    @Nullable String longitude
) {}
