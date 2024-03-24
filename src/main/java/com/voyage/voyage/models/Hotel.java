package com.voyage.voyage.models;

import javax.persistence.*;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prestataire_id")
    private Prestataire prestataire;

    private String nom;
    private String adresse;
    private String email;
    private Integer telephone;
    private Integer etoiles;
    private Integer chambresDisponibles;

    // Constructors, getters, setters, and other methods
}
