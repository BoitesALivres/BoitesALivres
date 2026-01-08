package fr.polytech.boitesalivres.backend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import fr.polytech.boitesalivres.backend.entities.Coordinate;
import fr.polytech.boitesalivres.backend.repositories.CoordinateRepository;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CoordinateService {

    private final CoordinateRepository coordinateRepository;

    public List<Coordinate> findAll() {
        return coordinateRepository.findAll();
    }

    public Optional<Coordinate> findById(Long id) {
        return coordinateRepository.findById(id);
    }

    public Optional<Coordinate> findByCoordinates(String latitude, String longitude) {
        return coordinateRepository.findByCoordinates(latitude, longitude);
    }

    public Coordinate save(Coordinate coordinate) {
        return coordinateRepository.save(coordinate);
    }

    public void deleteById(Long id) {
        coordinateRepository.deleteById(id);
    }
}
