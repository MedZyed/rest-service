package com.example.restservice.heroes.controller;

import com.example.restservice.heroes.mapper.HeroMapper;
import com.example.restservice.heroes.model.Hero;
import com.example.restservice.heroes.model.HeroEntity;
import com.example.restservice.heroes.repository.HeroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class HeroesControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(HeroesControllerV2.class);

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    private HeroMapper heroMapper;

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
            return heroRepository.findAll().stream().map(heroMapper::map).collect(Collectors.toList());
        }

        logger.info("Call to GET /heroes?name=" + searchText);
        return heroRepository.findByNameStartingWith(searchText).stream().map(heroMapper::map).collect(Collectors.toList());
    }

    @GetMapping("/heroes/{id}")
    public Hero getHero(@PathVariable("id") long id) {
        logger.info("Call to GET /heroes/" + id);
        Optional<HeroEntity> optHero = heroRepository.findById(id);
        return (optHero.isPresent()) ? heroMapper.map(optHero.get()) : null;
    }

    @PostMapping("/heroes")
    public Hero addHero(@RequestBody Hero newHero) {
        logger.info("Call to POST /heroes with body " + newHero.toString());
        if (newHero != null && !newHero.getName().isEmpty()) {
            logger.info("Saving " + newHero.toString());
            HeroEntity savedHero = heroRepository.save(new HeroEntity(newHero.getName()));
            return (savedHero != null) ? heroMapper.map(savedHero) : null;
        }
        return null;
    }

    @PutMapping("/heroes")
    public Hero updateHero(@RequestBody Hero updatedHero) {
        logger.info("Call to PUT /heroes with body " + updatedHero.toString());
        Optional<HeroEntity> optHero = heroRepository.findById(updatedHero.getId());
        if (optHero.isPresent()) {
            HeroEntity heroToUpdate = optHero.get();
            heroToUpdate.setName(updatedHero.getName());
            HeroEntity savedHero = heroRepository.save(heroToUpdate);
            return (savedHero != null) ? heroMapper.map(savedHero) : null;
        }
        return null;
    }

    @DeleteMapping("/heroes/{id}")
    public void deleteHero(@PathVariable("id") long id) {
        logger.info("Call to DELETE /heroes/" + id);
        heroRepository.deleteById(id);
    }
}
