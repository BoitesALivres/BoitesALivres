package fr.polytech.boitesalivres.backend.services.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.polytech.boitesalivres.backend.dtos.LoginRequest;
import fr.polytech.boitesalivres.backend.dtos.RegisterRequest;
import fr.polytech.boitesalivres.backend.entities.User;
import fr.polytech.boitesalivres.backend.exceptions.EmailAlreadyExistsException;
import fr.polytech.boitesalivres.backend.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public User authenticateAndGetUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        return user;
    }


    @Transactional
    public User registerAndGetUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        User user = User.builder()
                .username(request.username())
                .firstName(request.first_name())
                .lastName(request.last_name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return user;
    }
}