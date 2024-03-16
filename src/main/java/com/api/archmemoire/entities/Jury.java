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
public class Jury implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "STATUT")
    private STATUT statut;

    @Column(name = "GRADE")
    private GRADE grade;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "JURY_MEMOIRE",
            joinColumns = @JoinColumn(name = "jury_ID"),
            inverseJoinColumns = @JoinColumn(name = "memoires_ID")
    )
    @JsonIgnore
    private List<Memoire> memoires = new ArrayList<>();
}
