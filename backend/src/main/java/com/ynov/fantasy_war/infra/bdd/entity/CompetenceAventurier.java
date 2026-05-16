package com.ynov.fantasy_war.infra.bdd.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "competences_aventurier")
public class CompetenceAventurier {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, name = "id_competence")
    private UUID idCompetence;

    @Column(nullable = false, name = "id_aventurier")
    private UUID idAventurier;
}
