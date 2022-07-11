package com.example.restservice.heroes.mapper;

import com.example.restservice.heroes.model.Hero;
import com.example.restservice.heroes.model.HeroEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeroMapper {

    Hero map(HeroEntity heroEntity);
    HeroEntity map(Hero hero);
}
