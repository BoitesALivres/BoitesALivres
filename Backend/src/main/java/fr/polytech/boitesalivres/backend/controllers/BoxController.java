package fr.polytech.boitesalivres.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.polytech.boitesalivres.backend.dtos.BoxRequest;
import fr.polytech.boitesalivres.backend.dtos.BoxResponse;
import fr.polytech.boitesalivres.backend.entities.Box;
import fr.polytech.boitesalivres.backend.entities.Coordinate;
import fr.polytech.boitesalivres.backend.mappers.BoxMapper;
import fr.polytech.boitesalivres.backend.repositories.BoxRepository;
import fr.polytech.boitesalivres.backend.services.BoxService;
import fr.polytech.boitesalivres.backend.services.CoordinateService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;
    private final CoordinateService coordinateService;
    private final BoxMapper boxMapper;
    private final BoxRepository boxRepository;

    @GetMapping
    public List<BoxResponse> getAllBoxes() {
        List<BoxResponse> responses = boxService.findAll().stream()
                .map(box -> {
                    try {
                        return boxMapper.toResponse(box);
                    } catch (Exception e) {
                        throw e;
                    }
                })
                .toList();
        return responses;
    }

    @PostMapping
    public BoxResponse createBox(@Valid @RequestBody BoxRequest request) {
        var coordinate = coordinateService.findByCoordinates(request.latitude(), request.longitude())
                .orElseGet(() -> coordinateService.save(new Coordinate(null, request.latitude(), request.longitude(), null)));

        Box box = Box.builder()
                .name(request.name())
                .description(request.description())
                .quantity(request.quantity())
                .coordinate(coordinate)
                .build();
        Box saved = boxService.save(box);
        return boxMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoxResponse> updateBox(@PathVariable Long id, @Valid @RequestBody BoxRequest request) {
        var existingBox = boxService.findById(id);
        if (existingBox.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var coordinate = coordinateService.findByCoordinates(request.latitude(), request.longitude())
                .orElseGet(() -> coordinateService.save(new Coordinate(null, request.latitude(), request.longitude(), null)));

        Box box = existingBox.get();
        box.setName(request.name());
        box.setDescription(request.description());
        box.setQuantity(request.quantity());
        box.setCoordinate(coordinate);
        Box updated = boxService.save(box);
        return ResponseEntity.ok(boxMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        if (!boxRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        boxService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
