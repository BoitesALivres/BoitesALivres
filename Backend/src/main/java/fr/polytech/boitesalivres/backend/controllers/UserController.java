package fr.polytech.boitesalivres.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import fr.polytech.boitesalivres.backend.dtos.UserRequest;
import fr.polytech.boitesalivres.backend.dtos.UserResponse;
import fr.polytech.boitesalivres.backend.entities.User;
import fr.polytech.boitesalivres.backend.mappers.UserMapper;
import fr.polytech.boitesalivres.backend.services.UserService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(userMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        User user = userMapper.toEntity(request);
        User saved = userService.save(user);
        return userMapper.toResponse(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = userMapper.toEntity(request);
        user.setId(id);
        User updated = userService.save(user);
        return ResponseEntity.ok(userMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
