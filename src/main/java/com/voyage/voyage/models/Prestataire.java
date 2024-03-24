package com.voyage.voyage.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Prestataire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String adresse;
    private String email;
    private Integer telephone;
    private String raisonSociale;

    @Enumerated(EnumType.STRING)
    private TypePrestataire typePrestataire;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL)
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "prestataire", cascade = CascadeType.ALL)
    private List<TravelAgency> travelAgencies;

    // Constructors, getters, setters, and other methods

    public enum TypePrestataire {
        TYPE_A,
        TYPE_B,
        TYPE_C
        // Add more types if needed
    }
}
