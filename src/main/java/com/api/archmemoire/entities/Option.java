package com.api.archmemoire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "OPTIONS")
public class Option implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "DESCRIPTIONFRENCH")
    private String descriptionFrench;

    @Column(name = "DESCRIPTIONENGLISH")
    private String descriptionEnglish;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "DEPARTEMENT_ID")
    private Departement departement;
    
    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Parcours> parcours = new ArrayList<>();
}
