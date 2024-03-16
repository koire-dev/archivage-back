package com.api.archmemoire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cycle implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALEUR", unique = true)
    private Integer valeur;

    @Column(name = "DESCRIPTION", unique = true)
    private String description;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @OneToMany(mappedBy = "cycle", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Niveau> niveaux = new ArrayList<>();

}
