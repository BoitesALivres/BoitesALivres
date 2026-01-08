package fr.polytech.boitesalivres.backend.dtos;

public record ReservationResponse(
    Long userId,
    String username,
    Long boxId,
    String boxName,
    Integer reservation
) {}
