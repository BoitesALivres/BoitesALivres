package fr.polytech.boitesalivres.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import me.paulschwarz.springdotenv.DotenvPropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableJpaAuditing
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(BackendApplication.class);
        app.addInitializers(ctx -> {
            ConfigurableEnvironment env = ctx.getEnvironment();
            DotenvPropertySource.addToEnvironment(env);
        });
        app.run(args);
    }
}
