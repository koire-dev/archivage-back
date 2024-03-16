package com.api.archmemoire.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Departement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "ENGLISHDESCRIPTION")
    private String englishDescription;

    @Column(name = "FRENCHDESCRIPTION")
    private String frenchDescription;

    @Column(name = "ACTIVE")
    private Boolean active = true;
    
    @OneToMany(mappedBy = "departement", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Option> options = new ArrayList<>();
}
