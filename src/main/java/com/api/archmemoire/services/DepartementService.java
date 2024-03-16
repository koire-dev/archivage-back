package com.api.archmemoire.services;

import com.api.archmemoire.entities.Departement;
import com.api.archmemoire.entities.Option;
import com.api.archmemoire.entities.Parcours;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.DepartementRepo;
import com.api.archmemoire.repositories.OptionRepo;
import com.api.archmemoire.repositories.ParcoursRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartementService {

    private DepartementRepo departementRepo;
    private ParcoursRepo parcoursRepo;
    private OptionRepo optionRepo;

    @Autowired
    public DepartementService(DepartementRepo departementRepo, ParcoursRepo parcoursRepo, OptionRepo optionRepo) {
        this.departementRepo = departementRepo;
        this.parcoursRepo = parcoursRepo;
        this.optionRepo = optionRepo;
    }

    public Departement addDepartement(Departement departement) {
        if (departement == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        return departementRepo.save(departement);
    }

    public List<Departement> getAll() {
        List<Departement> list = departementRepo.findAll();
        if (list == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return list;
    }

    public Departement getByCode(String code) {
        Departement departement = departementRepo.findByCode(code).orElse(null);
        if (departement == null){
            throw new NotFoundException("Aucun departement avec le code : "+code + " dans la base de donnee");
        }
        return departement;
    }

    public Departement getById(Long id) {
        Departement departement = departementRepo.findById(id).orElse(null);
        if (departement == null){
            throw new NotFoundException("Aucun departement avec l'id : "+id + " dans la base de donnee");
        }
        return departement;
    }

    public Departement updateById(Long id, Departement departement) {
        Departement update = getById(id);
        if (update == null){
            throw new NotFoundException("Aucun departement avec l'id : "+id + " dans la base de donnee");
        }
        update.setActive(departement.getActive());
        update.setCode(departement.getCode());
        update.setEnglishDescription(departement.getEnglishDescription());
        update.setFrenchDescription(departement.getFrenchDescription());

        return departementRepo.save(update);
    }

    public String delete(Long id) {
        Departement departement = getById(id);
        if (departement == null){
            throw new NotFoundException("Aucun departement avec l'id : "+id + " dans la base de donnee");
        }
        departement.setActive(false);
        departementRepo.save(departement);

        return "Operation reussi avec succes";
    }

    public Departement getByCodeAndActive(String code, boolean b) {
        Departement departement = departementRepo.findByCodeAndActive(code, b);
        if (departement == null){
            throw new NotFoundException("Aucun departement actif avec le code : "+code + " dans la base de donnee");
        }
        return departement;
    }

    public List<Departement> getAllActif() {
        List<Departement> departementList = getAll();
        if (departementList.isEmpty()){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        List<Departement> departements = new ArrayList<>();
        for (Departement departement : departementList){
            if (departement.getActive().equals(true)){
                departements.add(departement);
            }
        }
        if (departements.isEmpty()){
            throw new NotFoundException("Aucun departement actif dans la base de donnee");
        }
        return departements;
    }

    public Departement getByParcours(String label) {
        Parcours parcours = parcoursRepo.findByLabel(label).orElse(null);
        if (parcours == null){
            throw new NotFoundException("Aucun parcours avec le label : "+label + " dans la base de donnee");
        }
        Option option = optionRepo.findById(parcours.getOption().getId()).orElse(null);
        if (option == null){
            throw new NotFoundException("Aucune option pour ce parcours dans la base de donnee");
        }
        return option.getDepartement();
    }
}
