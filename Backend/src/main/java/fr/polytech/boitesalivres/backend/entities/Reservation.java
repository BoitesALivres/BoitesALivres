package fr.polytech.boitesalivres.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import fr.polytech.boitesalivres.backend.utils.keys.ReservationId;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reservation")
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Reservation {

    @EmbeddedId
    private ReservationId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_reservation_user"))
    private User user;

    @ManyToOne
    @MapsId("boxId")
    @JoinColumn(name = "box_id", foreignKey = @ForeignKey(name = "fk_reservation_box"))
    private Box box;

    @Column(nullable = false)
    private Integer reservation;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true)
    private LocalDateTime updatedAt;
}
