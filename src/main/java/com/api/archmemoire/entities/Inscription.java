package com.api.archmemoire.entities;

import jakarta.persistence.*;
import java.io.Serializable;
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
@Table(name = "INSCRIPTION", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ETUDIANT_ID", "ANNEEACADEMIQUE_ID", "PARCOURS_ID"}, name = "UNQ_INSCRIPTION_0")
})
public class Inscription implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACTIVE")
    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "ANNEEACADEMIQUE_ID")
    private AnneeAcademique anneeAcademique;

    @ManyToOne
    @JoinColumn(name = "ETUDIANT_ID")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "PARCOURS_ID")
    private Parcours parcours;  
}
