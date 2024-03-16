package com.api.archmemoire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Memoire implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "THEME", unique = true, nullable = false)
    private String theme;

    @Column(name = "URLFILE", unique = true, nullable = false)
    private String urlFile;

    @Column(name = "VUE")
    private Integer vue = 0;

    @Column(name = "TELECHARGEMENT")
    private Integer telechargement = 0;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "CATEGORIE_ID")
    private Categorie categorie;

    @ManyToOne
    @JoinColumn(name = "ETUDIANT_ID")
    private Etudiant etudiant;

    @ManyToMany(mappedBy = "memoires", cascade = CascadeType.ALL)
    private List<Jury> jury = new ArrayList<>();

    @ManyToMany(mappedBy = "memoires", cascade = CascadeType.ALL)
    private List<KeyWorlds> keyWorlds = new ArrayList<>();
}
