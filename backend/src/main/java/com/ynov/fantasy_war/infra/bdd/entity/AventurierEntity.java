package com.ynov.fantasy_war.infra.bdd.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "aventuriers",
        indexes = {
            @Index(name = "idx_aventurier_nom", columnList = "nom")})
            
public class AventurierEntity {

    @Id // jakarta.persistence.Id;
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 120)
    private String nom;
    @Column(length = 500)
    private String description;
    @Column(nullable = false)
    private int niveau;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ClasseEntity classe;
    @Column(nullable = false)
    private int physique;
    @Column(nullable = false)
    private int mental;
    @Column(nullable = false)
    private int perception;

}
