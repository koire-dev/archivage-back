package com.api.archmemoire.services;

import com.api.archmemoire.entities.AnneeAcademique;
import com.api.archmemoire.entities.Etudiant;
import com.api.archmemoire.entities.Inscription;
import com.api.archmemoire.entities.Parcours;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscriptionService {

    private InscriptionRepo inscriptionRepo;
    private EtudiantRepo etudiantRepo;
    private AnneeAcademiqueRepo anneeAcademiqueRepo;
    private ParcoursRepo parcoursRepo;
    private OptionRepo optionRepo;
    private NiveauRepo niveauRepo;

    @Autowired
    public InscriptionService(InscriptionRepo inscriptionRepo, ParcoursRepo parcoursRepo, EtudiantRepo etudiantRepo, AnneeAcademiqueRepo anneeAcademiqueRepo, OptionRepo optionRepo, NiveauRepo niveauRepo) {
        this.inscriptionRepo = inscriptionRepo;
        this.parcoursRepo = parcoursRepo;
        this.etudiantRepo = etudiantRepo;
        this.anneeAcademiqueRepo = anneeAcademiqueRepo;
        this.optionRepo = optionRepo;
        this.niveauRepo = niveauRepo;
    }


    public Inscription addInscription(Inscription inscription) {
        if (inscription == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        return inscriptionRepo.save(inscription);
    }

    public Inscription getById(Long id) {
        Inscription inscription = inscriptionRepo.findById(id).orElse(null);
        if (inscription == null){
            throw new NotFoundException("Aucune inscription avec l'id : "+id + " dans la base de donnee");
        }
        return inscription;
    }

    public Inscription update(Long id, Inscription inscription) {
        Inscription inscriptionFromDb = inscriptionRepo.findById(id).orElse(null);
        if (inscriptionFromDb == null){
            throw new NotFoundException("Aucune inscription avec l'id : "+id + " dans la base de donnee");
        }
        inscriptionFromDb.setEtudiant(inscription.getEtudiant());
        inscriptionFromDb.setAnneeAcademique(inscription.getAnneeAcademique());
        inscriptionFromDb.setParcours(inscription.getParcours());

        return inscriptionRepo.save(inscriptionFromDb);
    }

    public String delete(Long id) {
        Inscription inscription = getById(id);
        if (inscription == null){
            throw new NotFoundException("Aucune inscription avec l'id : "+id + " dans la base de donnee");
        }
        inscription.setActive(false);
        inscriptionRepo.save(inscription);

        return "Operation reussi avec succes";
    }

    public Inscription getByEtudiantAndAnneeAcademique(Etudiant etudiant, AnneeAcademique anneeAcademique) {
        Inscription inscription = inscriptionRepo.findByEtudiantAndAnneeAcademique(etudiant, anneeAcademique);
        if (inscription == null){
            throw new NotFoundException("Aucune inscription de l'etudiant : "+etudiant.getMatricule() + " dans la base de donnee pour l'annee : "+anneeAcademique.getCode());
        }
        return inscription;
    }

    public List<Inscription> getListInscriptionByParcoursAndAnnee(String label, String code){
        Parcours parcours = parcoursRepo.findByLabel(label).orElse(null);
        AnneeAcademique anneeAcademique = anneeAcademiqueRepo.findByCode(code).orElse(null);
        if (parcours == null){
            throw new NotFoundException("Aucun objet avec le parcours: "+label +" dans la base de donnee");
        }
        if (anneeAcademique == null){
            throw new NotFoundException("Aucun objet avec le code: "+label +" dans la base de donnee");
        }
        List<Inscription> inscriptionList = inscriptionRepo.findAllByParcoursAndAnneeAcademique(parcours, anneeAcademique);
        return inscriptionList;
    }
}