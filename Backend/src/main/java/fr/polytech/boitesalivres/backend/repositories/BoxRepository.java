package fr.polytech.boitesalivres.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import fr.polytech.boitesalivres.backend.entities.Box;
import java.util.Optional;

@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
    @Query("SELECT b FROM Box b LEFT JOIN FETCH b.coordinate")
    java.util.List<Box> findAllWithCoordinates();

    Optional<Box> findByName(String name);

    @Query("SELECT b FROM Box b LEFT JOIN FETCH b.coordinate WHERE b.id = :id")
    Optional<Box> findByIdWithCoordinates(Long id);

    @Query("SELECT b FROM Box b LEFT JOIN FETCH b.coordinate WHERE b.coordinate.latitude = :latitude AND b.coordinate.longitude = :longitude")
    Optional<Box> findByCoordinates(String latitude, String longitude);

    @Modifying
    @Query("DELETE FROM Box b WHERE b.id = :id")
    void deleteBoxById(Long id);
}