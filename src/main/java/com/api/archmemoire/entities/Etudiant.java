package com.api.archmemoire.entities;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author COMPUTER STORES
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Etudiant implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATEDENAISSANCE")
    private Date dateDeNaissance;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "GENRE")
    private GENRE genre;

    @Column(name = "LIEUDENAISSANCE")
    private String lieuDeNaissance;

//    auto
    @Column(name = "MATRICULE", unique = true)
    private String matricule;

    @Column(name = "NOM")
    private String nom;
    
    @Column(name = "REGION")
    private String region;

    @Column(name = "NUMEROTELEPHONE")
    private String numeroTelephone;

    @Column(name = "NATIONALITE")
    private String nationalite;

    @Column(name = "TYPE")
    private TYPE type;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Memoire> memoires = new ArrayList<>();

    @OneToMany(mappedBy = "etudiant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Inscription> inscriptions = new ArrayList<>();

}
