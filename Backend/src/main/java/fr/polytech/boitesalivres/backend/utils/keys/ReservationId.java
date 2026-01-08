package fr.polytech.boitesalivres.backend.utils.keys;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class ReservationId implements Serializable {
    private Long userId;
    private Long boxId;

    public ReservationId(Long userId, Long boxId) {
        this.userId = userId;
        this.boxId = boxId;
    }
}
