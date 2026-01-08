package fr.polytech.boitesalivres.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.polytech.boitesalivres.backend.dtos.ReservationRequest;
import fr.polytech.boitesalivres.backend.dtos.ReservationResponse;
import fr.polytech.boitesalivres.backend.entities.Reservation;
import fr.polytech.boitesalivres.backend.mappers.ReservationMapper;
import fr.polytech.boitesalivres.backend.services.ReservationService;
import fr.polytech.boitesalivres.backend.utils.keys.ReservationId;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @GetMapping
    public List<ReservationResponse> getAllReservations() {
        return reservationService.findAll().stream()
                .map(reservationMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ReservationResponse createReservation(@Valid @RequestBody ReservationRequest request) {
        Reservation reservation = reservationMapper.toEntity(request);
        Reservation saved = reservationService.save(reservation);
        return reservationMapper.toResponse(saved);
    }

    @DeleteMapping("/{userId}/{boxId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long userId, @PathVariable Long boxId) {
        ReservationId id = new ReservationId(userId, boxId);
        if (!reservationService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
