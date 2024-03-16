package com.api.archmemoire.services;

import com.api.archmemoire.entities.*;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.CycleRepo;
import com.api.archmemoire.repositories.EtudiantRepo;
import com.api.archmemoire.repositories.NiveauRepo;
import com.api.archmemoire.repositories.ParcoursRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParcoursService {

    private ParcoursRepo parcoursRepo;
    private OptionService optionService;
    private EtudiantRepo etudiantRepo;
    private AnneeAcademiqueService anneeAcademiqueService;
    private InscriptionService inscriptionService;
    private DepartementService departementService;
    private CycleRepo cycleRepo;
    private NiveauRepo niveauRepo;


    @Autowired
    public ParcoursService(ParcoursRepo parcoursRepo, DepartementService departementService, OptionService optionService, EtudiantRepo etudiantRepo, AnneeAcademiqueService anneeAcademiqueService, InscriptionService inscriptionService, CycleRepo cycleRepo, NiveauRepo niveauRepo) {
        this.optionService = optionService;
        this.etudiantRepo = etudiantRepo;
        this.anneeAcademiqueService = anneeAcademiqueService;
        this.inscriptionService = inscriptionService;
        this.parcoursRepo = parcoursRepo;
        this.departementService = departementService;
        this.cycleRepo = cycleRepo;
        this.niveauRepo = niveauRepo;
    }

    public Parcours getParcoursByOptionAndNiveau(int level, String code){

        Option option = optionService.getByCode(code);
        Niveau niveau = niveauRepo.findByValeur(level).orElse(null);

        if (option == null || niveau == null) {
            throw new NotFoundException("Aucun resultat avec ces donnees");
        }
        Parcours parcours = parcoursRepo.findByOptionAndNiveau(option, niveau).orElse(null);
        return parcours;
    }

    public Parcours getParcoursEtudiant(Long id, String anneeAca){
        Etudiant etudiant = etudiantRepo.findById(id).orElse(null);
        AnneeAcademique anneeAcademique = anneeAcademiqueService.getByCode(anneeAca);
        if (etudiant == null || anneeAcademique == null){
            throw new NotFoundException("Aucun resultat avec ces donnees");
        }
        return inscriptionService.getByEtudiantAndAnneeAcademique(etudiant, anneeAcademique).getParcours();
    }

    public Parcours getByLabel(String label) {
        Parcours parcours = parcoursRepo.findByLabel(label).orElse(null);
        if (parcours == null){
            throw new NotFoundException("Aucun parcours avec le label: "+ label);
        }
        return parcours;
    }

    public Parcours addParcours(Parcours parcours) {
        if (parcours == null){
            throw new NotFoundException("Remplissez correctement tous les champs");
        }

        if (parcours.getLabel() != null){
            return parcoursRepo.save(parcours);
        }
        Niveau niveau = niveauRepo.findById(parcours.getNiveau().getId()).orElse(null);
        if (niveau == null){
            throw new NotFoundException("Ce parcours n'est pas correctement enregistre dans la base de donnees, veuillez le rattacher a un niveau");
        }
        Option option = optionService.getById( parcours.getOption().getId());
        String code = option.getCode();
        int valeur = niveau.getValeur();
        String label = code+" "+valeur;
        parcours.setLabel(label);
        return parcoursRepo.save(parcours);
    }

    public List<Parcours> getAll() {
        List<Parcours> list = parcoursRepo.findAll();
        if (list == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnees");
        }
        return list;
    }

    public Parcours getById(Long id) {
        Parcours parcours = parcoursRepo.findById(id).orElse(null);
        if (parcours == null){
            throw new NotFoundException("Aucun resultat avec l'id: "+ id);
        }
        return parcours;
    }

    public List<Parcours> getAllByDepartement(String code) {
        Departement departement = departementService.getByCode(code);
        if (departement == null){
            throw new NotFoundException("Aucun resultat avec le code: "+ code);
        }
        List<Parcours> parcoursList = new ArrayList<>();
        for (Parcours parcours : parcoursRepo.findAll()){
            Option option = optionService.getById(parcours.getOption().getId());
            Departement depart = departementService.getById(option.getDepartement().getId());
            if (depart.getCode().equals(departement.getCode())){
                parcoursList.add(parcours);
            }
        }
        if (parcoursList == null){
            throw new NotFoundException("Aucun parcours pour ce departement");
        }
        return parcoursList;
    }

    public String delete(Long id) {
        Parcours parcours = getById(id);
        if (parcours == null){
            throw new NotFoundException("Aucun resultat avec l'id: "+ id);
        }
        parcours.setActive(false);
        parcoursRepo.save(parcours);

        return "Operation reussi avec succes";
    }

}
