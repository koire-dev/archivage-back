package com.api.archmemoire.services;

import com.api.archmemoire.entities.Categorie;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.CategorieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategorieService {

    private CategorieRepo categorieRepo;

    @Autowired
    public CategorieService(CategorieRepo categorieRepo) {
        this.categorieRepo = categorieRepo;
    }

    public Categorie saveCategorie(Categorie categorie){
        if (categorie == null || categorie.getLabel() == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        return categorieRepo.save(categorie);
    }

    public Categorie getCategorieById(Long id){
        Categorie categorie = categorieRepo.findById(id).orElse(null);
        if (categorie == null){
            throw new NotFoundException("Aucun objet avec l'id: "+id + " dans la base de donnee");
        }
        return categorie;
    }

    public Categorie getCategorieByLabel(String label){
        Categorie categorie = categorieRepo.findByLabel(label);
        if (categorie == null){
            throw new NotFoundException("Aucun enregistrement avec le label: " + label+ " dans la base de donnee");
        }
        return categorie;
    }

    public List<Categorie> getAllCategorie(){
        if (categorieRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return categorieRepo.findAll();
    }

    public Categorie editCategorie(Long id, Categorie categorie){
        Categorie result = categorieRepo.findById(id).orElse(null);
        if (result == null){
            throw new NotFoundException("Aucun objet avec l'id: "+id + " dans la base de donnee");
        }
        result.setActive(categorie.getActive());
        result.setLabel(categorie.getLabel());

        return categorieRepo.save(result);
    }

    public String dropCategorie(Long id){
        Categorie categorie = categorieRepo.findById(id).orElse(null);
        if (categorie == null){
            throw new NotFoundException("Aucun objet avec l'id: "+id + " dans la base de donnee");
        }
        categorie.setActive(false);
        categorieRepo.save(categorie);
        return "Desactivation reussi";
    }
}
