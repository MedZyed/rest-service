package com.example.restservice.heroes.model;

import javax.persistence.*;

@Entity
@Table(name = "hero")
public class HeroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;

    public HeroEntity() {
    }

    public HeroEntity(String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HeroEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
