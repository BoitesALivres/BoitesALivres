package fr.polytech.boitesalivres.backend.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "coordinate")
@Data
@Entity
public class Coordinate {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String latitude;

    @Column(nullable = false)
    @NotBlank
    private String longitude;

    @OneToOne(mappedBy = "coordinate")
    @JsonBackReference
    private Box box;

}
