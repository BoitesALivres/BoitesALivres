package fr.polytech.boitesalivres.backend.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import fr.polytech.boitesalivres.backend.entities.Reservation;
import fr.polytech.boitesalivres.backend.utils.keys.ReservationId;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    boolean existsByBoxId(Long boxId);

    boolean existsByBoxIdAndUserId(Long boxId, Long userId);

    @Modifying
    @Query("DELETE FROM Reservation r WHERE r.box.id = :boxId")
    void deleteByBoxId(Long boxId);

    @Override
    @EntityGraph(attributePaths = {"user", "box"})
    List<Reservation> findAll();
}