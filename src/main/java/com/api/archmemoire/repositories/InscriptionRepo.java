package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.AnneeAcademique;
import com.api.archmemoire.entities.Etudiant;
import com.api.archmemoire.entities.Inscription;
import com.api.archmemoire.entities.Parcours;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscriptionRepo extends JpaRepository<Inscription, Long> {

    Inscription findByEtudiantAndAnneeAcademique(Etudiant etudiant, AnneeAcademique anneeAcademique);

    List<Inscription> findAllByAnneeAcademique(AnneeAcademique anneeAcademique);

    List<Inscription> findAllByParcoursAndAnneeAcademiqueAndActive(Parcours parcours, AnneeAcademique anneeAcademique, boolean b);

    List<Inscription> findAllByParcoursAndAnneeAcademique(Parcours parcours, AnneeAcademique anneeAcademique);
}
