package fr.polytech.boitesalivres.backend.seeders;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import fr.polytech.boitesalivres.backend.entities.Box;
import fr.polytech.boitesalivres.backend.entities.Coordinate;
import fr.polytech.boitesalivres.backend.repositories.BoxRepository;
import fr.polytech.boitesalivres.backend.repositories.CoordinateRepository;

@Order(3)
@Configuration
@RequiredArgsConstructor
public class BoxSeeder implements ApplicationRunner {
    private final BoxRepository boxRepository;
    private final CoordinateRepository coordinateRepository;
    private static final Logger log = LoggerFactory.getLogger(BoxSeeder.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.getOptionValues("seeder") != null){
            var seeder = args.getOptionValues("seeder").get(0);
            if(seeder.contains("BoxSeeder")) {
                seedBoxes();
                log.info("Success run BoxSeeder seeder");
            }
        }
    }

    private void seedBoxes(){
        Object[][] boxes = new Object[][]{
                {"Boite à lire du CEST", 12, "CEST (Centre d'Education Sportive de Tours)", 1L},
                {"Borne Livr'Libre Savonnières", 21, "", 2L},
                {"Borne Livr' Libre Oratoire", 13, "La Fabrique de Livres", 3L},
                {"Borne Livr' Libre du parc de la Gloriette", 23, "Tours Métropole", 4L},
                {"Boite à livres square Constancia Fondettes", 17, "", 5L},
                {"Boite à livres Rue du 11 novembre La Riche", 23, "", 6L},
                {"Borne Livr' Libre du Jardin Boylesve", 22, "Comité de quartier Lakanal-Strasbourg", 7L},
                {"Boite à livre", 17, "", 8L},
                {"Borne Livr'Libre Berthenay", 24, "", 9L},
                {"Jardin botanique Naurod-Wiesbaden", 14, "", 10L},
                {"Borne Livr' Libre", 18, "", 11L},
                {"Borne Livr' Libre des Justices", 22, "Association Les Amis des Justices", 12L},
                {"Boîte à lire quartier Saint-François", 16, "l'association Les P'tits Pas", 13L},
                {"Boîte à lire du Jardin des Vikings", 18, "Comité de quartier Colbert-Cathédrale", 14L},
                {"Boîte à lire des Jardins Partagés", 19, "Association Le jardin des Milles pattes", 15L},
                {"Boîte à lire quartier de la Bergeonnerie", 16, "l'association L'Ardente - section Bebbo", 16L},
                {"Ecole Pérochon", 13, "Association Les Douets de Tours", 17L},
                {"Borne Livr' Libre du Jardin de l'éléphant Fritz", 17, "Association Commune Libre de Chanzy", 18L},
                {"Boite à Livre Place des déportés", 19, "", 19L},
                {"Borne Livr' Libre du Jardin Botanique", 11, "Société d'Horticulture de Touraine", 20L},
                {"Boîte à livres de la papoterie", 23, "", 21L},
                {"Borne Livr' Libre Parc de la Tour", 22, "", 22L},
                {"Borne Livr' Libre de Montjoyeux", 18, "Association du marché de Montjoyeux", 23L},
                {"Boîte à livres du 7 rue Henri Dunant", 14, "", 24L},
                {"Boite à lire Hôpital Bretonneau (devant le bâtiment b3 face au relais H)", 24, "", 25L},
                {"Boîte à livres de l'école Maryse Bastié", 20, "", 26L},
                {"Borne Livr' Libre du Jardin Sisley", 19, "Comité de quartier des Fontaines", 27L},
                {"Borne Livr' Libre", 23, "", 28L},
                {"Boîte à lire Pavillon-Fontaine Pottier", 18, "", 29L},
                {"Borne Livr'Libre Parcay-Meslay", 11, "", 30L},
                {"Borne Livr' Libre du centre socioculturel Léo Lagrange Gentiana", 23, "Léo Lagrange Gentiana", 31L},
                {"La Boîte à Lire", 24, "Association Douets Milletière", 32L},
                {"Borne Livr' Libre du Square Mendellsohn", 10, "Accueil de Loisirs Vigny-Musset", 33L},
                {"Borne Livr' Libre Paul Bert", 22, "Comité de quartier « L'autre rive Paul Bert-Ile Aucard »", 34L},
                {"Borne Livr' Libre du Jardin des Prébendes", 23, "Association Vie Active aux Prébendes", 35L},
                {"Parvis de la Gare", 23, "", 36L},
                {"Borne Livr' Libre du Jardin Salancy (Jardin Velpeau)", 20, "Velpeau en Transition", 37L},
                {"Boîte à livres Rue Eugène Gouin Fondettes", 23, "", 38L},
                {"Boite à lire Courteline", 14, "Association Courteline", 39L},
                {"Boîte à livres dans le Centre social Equinoxe", 23, "Centre social Equinoxe", 40L},
                {"Boîte à livres du Centre social du Morier", 17, "Centre social du Morier", 41L},
                {"Boîte à livres des perriers", 22, "", 42L},
                {"Borne Livr' Libre Parc de la Perraudière St Cyr", 17, "", 43L},
                {"Borne Livr' Libre Sainte-Radegonde", 24, "Comité de quartier Vivre Ensemble à Ste Radegonde", 44L},
                {"Borne Livr' Libre Centre Social Pluriel(le)s", 11, "Centre Social Pluriel(le)s", 45L},
                {"Borne Livr' Libre place Louvois", 23, "Association Les Douets de Tours", 46L},
                {"Borne Livr' Libre du Jardin Jacques Monod", 10, "Comité de quartier « Vivre des Deux Lions »", 47L},
                {"Borne Livr'Libre Saint-Genouph", 19, "", 48L},
                {"Borne Livr' Libre Jardin de la Grenouillère", 23, "Association Monconseil", 49L},
                {"Boîte à lire quartier Febvotte", 17, "Comité de quartier Febvotte-Marat", 50L},
                {"La Boîte à Lire  du Carroi aux Herbes", 11, "Association Victoire en Transition", 51L},
                {"Borne Livr' Libre rue Charles Picart le Doux", 13, "Comité Syndical Vert Village Tours Nord", 52L}
        };


        long coordinateCount = coordinateRepository.count();
        if (coordinateCount < boxes.length) {
            log.error("BoxSeeder aborted: only " + coordinateCount + " coordinates found, but " + boxes.length + " are required. Run CoordinateSeeder first.");
            return;
        }

        for (Object[] b : boxes) {
            Coordinate coord = coordinateRepository.findById((Long)b[3]).orElseThrow();

            Box box = Box.builder()
                .name((String)b[0])
                .quantity((Integer)b[1])
                .coordinate(coord)
                .build();

            if(b[2] != null && !((String)b[2]).isBlank()){
                box.setDescription((String)b[2]);
            }

            boxRepository.save(box);

            log.info("Seeded box: " + box.getName() + " (id: " + box.getId() + ")");
        }
    }
}
