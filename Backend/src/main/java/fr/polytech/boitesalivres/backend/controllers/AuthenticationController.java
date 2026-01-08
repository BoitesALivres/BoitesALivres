package fr.polytech.boitesalivres.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import fr.polytech.boitesalivres.backend.dtos.AuthenticationResponse;
import fr.polytech.boitesalivres.backend.dtos.LoginRequest;
import fr.polytech.boitesalivres.backend.dtos.RegisterRequest;
import fr.polytech.boitesalivres.backend.dtos.UserResponse;
import fr.polytech.boitesalivres.backend.entities.User;
import fr.polytech.boitesalivres.backend.mappers.UserMapper;
import fr.polytech.boitesalivres.backend.repositories.UserRepository;
import fr.polytech.boitesalivres.backend.services.authentication.AuthenticationService;
import fr.polytech.boitesalivres.backend.services.authentication.JwtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authenticationService.authenticateAndGetUser(request);

        ResponseCookie cookie = ResponseCookie.from("SESSION_TOKEN", jwtService.generateToken(user)).httpOnly(true)
                .secure(false).path("/").maxAge(24 * 60 * 60).sameSite("Lax").build();

        AuthenticationResponse response = userMapper.toAuthenticationResponse(user);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
    }


    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request) {
        User user = authenticationService.registerAndGetUser(request);

        ResponseCookie cookie = ResponseCookie.from("SESSION_TOKEN", jwtService.generateToken(user)).httpOnly(true)
                .secure(false).path("/").maxAge(24 * 60 * 60).sameSite("Lax").build();

        AuthenticationResponse response = userMapper.toAuthenticationResponse(user);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(response);
    }

    @GetMapping("me")
    public ResponseEntity<UserResponse> me(
            @CookieValue(name = "SESSION_TOKEN", required = false) String token) {
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(401).build();
        }

        User user = jwtService.getUserFromToken(token);
        User localUser = userRepository.findById(user.getId()).orElseThrow();

        return ResponseEntity.ok(userMapper.toResponse(localUser));
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = ResponseCookie.from("SESSION_TOKEN", "").httpOnly(true)
                .secure(false).path("/").maxAge(0).sameSite("Lax").build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

}
