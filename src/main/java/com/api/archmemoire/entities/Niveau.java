package com.api.archmemoire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
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
public class Niveau implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "VALEUR", unique = true)
    private Integer valeur;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "CYCLE_ID")
    private Cycle cycle;

    @OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Parcours> parcours = new ArrayList<>();

}
