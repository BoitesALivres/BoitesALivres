package fr.polytech.boitesalivres.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fr.polytech.boitesalivres.backend.entities.Reservation;
import fr.polytech.boitesalivres.backend.exceptions.BoxNotAvailableException;
import fr.polytech.boitesalivres.backend.repositories.ReservationRepository;
import fr.polytech.boitesalivres.backend.utils.keys.ReservationId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> findById(ReservationId id) {
        return reservationRepository.findById(id);
    }

    public Reservation save(Reservation reservation) {
        if (reservationRepository.existsByBoxIdAndUserId(reservation.getBox().getId(), reservation.getUser().getId())) {
        }
        return reservationRepository.save(reservation);
    }

    public void deleteById(ReservationId id) {
        reservationRepository.deleteById(id);
    }
}
