package fr.polytech.boitesalivres.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.polytech.boitesalivres.backend.entities.Box;
import fr.polytech.boitesalivres.backend.repositories.BoxRepository;
import fr.polytech.boitesalivres.backend.repositories.ReservationRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxService {

    private final BoxRepository boxRepository;
    private final ReservationRepository reservationRepository;

    public List<Box> findAll() {
        return boxRepository.findAllWithCoordinates();
    }

    public Optional<Box> findById(Long id) {
        return boxRepository.findByIdWithCoordinates(id);
    }

    public Optional<Box> findByName(String name) {
        return boxRepository.findByName(name);
    }

    public Optional<Box> findByCoordinates(String latitude, String longitude) {
        return boxRepository.findByCoordinates(latitude, longitude);
    }

    public Box save(Box box) {
        return boxRepository.save(box);
    }

    @Transactional
    public void deleteById(Long id) {
        reservationRepository.deleteByBoxId(id);
        boxRepository.deleteBoxById(id);
    }
}
