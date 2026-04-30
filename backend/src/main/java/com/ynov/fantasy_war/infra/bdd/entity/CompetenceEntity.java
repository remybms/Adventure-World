package com.ynov.fantasy_war.infra.bdd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(
        name = "competences",
        indexes = {
                @Index(name = "idx_competence_nom", columnList = "nom")})
public class CompetenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 120)
    private String nom;

    @Column(length = 500)
    private String description;

    @Column(length = 30)
    private ClasseEntity classeRequise;

    @Column
    private int niveauMinimum;

    @Column
    private int physiqueRequis = 1;

    @Column
    private int perceptionRequise = 1;

    @Column
    private int mentalRequis = 1;

    @Column
    private List<UUID> competencesRequises;

}
