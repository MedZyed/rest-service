package com.example.restservice.heroes.repository;

import com.example.restservice.heroes.model.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {

    List<HeroEntity> findByNameStartingWith(String searchText);
}
