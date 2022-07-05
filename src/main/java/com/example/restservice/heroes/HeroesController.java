package com.example.restservice.heroes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class HeroesController {

    private static final Logger logger = LoggerFactory.getLogger(HeroesController.class);

    List<Hero> heroesDB = new ArrayList() {{
        add(new Hero(12, "Dr. Nice"));
        add(new Hero(13, "Bombasto"));
        add(new Hero(14, "Celeritas"));
        add(new Hero(15, "Magneta"));
        add(new Hero(16, "RubberMan"));
        add(new Hero(17, "Dynama"));
        add(new Hero(18, "Dr. IQ"));
        add(new Hero(19, "Magma"));
        add(new Hero(20, "Tornado"));
    }};

    private int maxKey = 20;

    @GetMapping("/heroes")
    public List<Hero> getOrSearchHeroesByName(@RequestParam(value = "name", defaultValue = "") String searchText) {
        if (searchText.isEmpty()) {
            logger.info("Call to GET /heroes");
            return heroesDB;
        }

        logger.info("Call to GET /heroes?name=" + searchText);
        return heroesDB.stream().filter(hero -> hero.getName().contains(searchText)).collect(Collectors.toList());
    }

    @GetMapping("/heroes/{id}")
    public Hero getHero(@PathVariable("id") int id) {
        logger.info("Call to GET /heroes/" + id);
        Optional<Hero> heroFound = heroesDB.stream().filter(hero -> hero.getId() == id).findFirst();
        return (heroFound.isPresent()) ? heroFound.get() : null;
    }

    @PostMapping("/heroes")
    public Hero addHero(@RequestBody Hero newHero) {
        logger.info("Call to POST /heroes with body " + newHero.toString());
        if (newHero != null && !newHero.getName().isEmpty()) {
            // Add new hero to list
            Hero savedHero = new Hero(++maxKey, newHero.getName());
            logger.info("Saving " + savedHero.toString());
            heroesDB.add(savedHero);
            return savedHero;
        }
        return null;
    }

    @DeleteMapping("/heroes/{id}")
    public Hero deleteHero(@PathVariable("id") int id) {
        logger.info("Call to DELETE /heroes" + id);
        Optional<Hero> heroToDelete = heroesDB.stream().filter(hero -> hero.getId() == id).findFirst();
        if (heroToDelete.isPresent()) {

            Hero deletedHero = heroToDelete.get();

            // Delete from heroes list
            heroesDB.removeIf(hero -> hero.getId() == id);

            return deletedHero;
        }
        return null;
    }

    @PutMapping("/heroes")
    public Hero updateHero(@RequestBody Hero updatedHero) {
        logger.info("Call to PUT /heroes with body " + updatedHero.toString());
        Optional<Hero> heroToUpdate = heroesDB.stream().filter(hero -> hero.getId() == updatedHero.getId()).findFirst();
        if (heroToUpdate.isPresent()) {

            // Update hero in list
            heroesDB.stream().filter(hero -> hero.getId() == updatedHero.getId()).forEach(hero -> hero.setName(updatedHero.getName()));

            return updatedHero;
        }
        return null;
    }
}
