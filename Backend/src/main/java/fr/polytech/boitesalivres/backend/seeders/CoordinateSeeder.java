package fr.polytech.boitesalivres.backend.seeders;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import fr.polytech.boitesalivres.backend.entities.Coordinate;
import fr.polytech.boitesalivres.backend.repositories.CoordinateRepository;

@Order(2)
@Configuration
@RequiredArgsConstructor
public class CoordinateSeeder implements ApplicationRunner {
    private final CoordinateRepository coordinateRepository;
    private static final Logger log = LoggerFactory.getLogger(CoordinateSeeder.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            var seeder = args.getOptionValues("seeder").get(0);
            if(seeder.contains("CoordinateSeeder")) {
                seedCoordinates();
                log.info("Success run CoordinateSeeder seeder");
            }
        }
    }

    private void seedCoordinates(){
        Object[][] coordinates = new Object[][]{
            {"47.3903436256", "0.6961206773"},
            {"47.3484316", "0.5469802004"},
            {"47.412287683", "0.6982528837"},
            {"47.3677718003", "0.6758196996"},
            {"47.4044657004", "0.6008103005"},
            {"47.3876554002", "0.6590282996"},
            {"47.3814610996", "0.6813224994"},
            {"47.4083121724", "0.5995276227"},
            {"47.3634171004", "0.5258413003"},
            {"47.4048321348", "0.5930594064"},
            {"47.3932960004", "0.5560890001"},
            {"47.4067759007", "0.704340611"},
            {"47.3804171912", "0.6677654036"},
            {"47.3951941158", "0.6981740843"},
            {"47.4206334136", "0.686307519"},
            {"47.361713272", "0.6932933092"},
            {"47.4357721386", "0.6922343357"},
            {"47.3895711259", "0.6750999929"},
            {"47.3918535001", "0.7326701006"},
            {"47.3870862309", "0.6668131321"},
            {"47.3392263997", "0.7347258003"},
            {"47.4022408", "0.6705570996"},
            {"47.3612318447", "0.7071780127"},
            {"47.3491949002", "0.7314208002"},
            {"47.387050457", "0.6677591246"},
            {"47.3439745998", "0.7202037998"},
            {"47.3702670374", "0.7109076703"},
            {"47.38235", "0.5562230006"},
            {"47.4145114815", "0.690971412"},
            {"47.4413336128", "0.7414835455"},
            {"47.4139489003", "0.6857773999"},
            {"47.4336911711", "0.7007244289"},
            {"47.3732812378", "0.6971144822"},
            {"47.4029308004", "0.6939477996"},
            {"47.3844517138", "0.686212135"},
            {"47.3871406002", "0.7228583997"},
            {"47.3880334118", "0.7028367785"},
            {"47.4046346", "0.6004187006"},
            {"47.3935645003", "0.6759100999"},
            {"47.3889671998", "0.6583098995"},
            {"47.3514004996", "0.6699175002"},
            {"47.3487910001", "0.6957281998"},
            {"47.3997189998", "0.6691006995"},
            {"47.4024538017", "0.7111316296"},
            {"47.3801819574", "0.6952533842"},
            {"47.4348365537", "0.6790292444"},
            {"47.3696656743", "0.684867211"},
            {"47.3782491004", "0.5993846001"},
            {"47.4194280362", "0.6990902755"},
            {"47.3772914484", "0.6881232054"},
            {"47.3946964544", "0.6792198939"},
            {"47.412983335", "0.7063762313"}
        };

        for (Object[] c : coordinates) {
            Coordinate coordinate = Coordinate.builder()
                .latitude((String)c[0])
                .longitude((String)c[1])
                .build();
            coordinateRepository.save(coordinate);

            log.info("Seeded coordinate: " + coordinate.getId() + " (" + coordinate.getLatitude() + ", " + coordinate.getLongitude() + ")");
        }
    }
}
