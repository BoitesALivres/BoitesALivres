package fr.polytech.boitesalivres.backend.seeders;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import fr.polytech.boitesalivres.backend.entities.User;
import fr.polytech.boitesalivres.backend.repositories.UserRepository;
import java.util.Arrays;
import java.util.List;

@Order(1)
@Configuration
@RequiredArgsConstructor
public class UserTestSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(UserTestSeeder.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            List<String> seeder = Arrays.asList(args.getOptionValues("seeder").get(0).split(","));
            if(seeder.contains("UserTestSeeder")) {
                seedUserTest();
                log.info("Success run UserTestSeeder seeder");
            }
        }
    }

    private void seedUserTest(){
        User user = User.builder()
                .firstName("Test")
                .lastName("User")
                .email("user@example.com")
                .username("user")
                .password(passwordEncoder.encode("Password@1"))
                .build();

        userRepository.save(user);

        log.info("Seeded user: " + user.getEmail() + " with password: Password@1");
    }
}
