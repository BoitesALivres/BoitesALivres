package fr.polytech.boitesalivres.backend.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.polytech.boitesalivres.backend.validators.NullableNotBlank;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "box")
@Data
@Entity
public class Box {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @NullableNotBlank
    @Builder.Default
    private String description = null;

    @Column(nullable = false)
    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "coordinate_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_box_coordinate"))
    @JsonManagedReference
    private Coordinate coordinate;
}
