package fr.polytech.boitesalivres.backend.dtos;

import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
    @NotNull Long userId,
    @NotNull Long boxId,
    @NotNull Integer reservation
) {}
