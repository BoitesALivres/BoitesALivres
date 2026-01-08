package fr.polytech.boitesalivres.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fr.polytech.boitesalivres.backend.entities.Coordinate;
import java.util.Optional;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    @Query("SELECT c FROM Coordinate c WHERE c.latitude = :latitude AND c.longitude = :longitude")
    Optional<Coordinate> findByCoordinates(String latitude, String longitude);
}