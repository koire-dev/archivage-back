package com.api.archmemoire.services;

import com.api.archmemoire.entities.KeyWorlds;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.KeyWorldsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeyWorldsService {

    private KeyWorldsRepo keyWorldsRepo;

    @Autowired
    public KeyWorldsService(KeyWorldsRepo keyWorldsRepo) {
        this.keyWorldsRepo = keyWorldsRepo;
    }

    public KeyWorlds saveKeyWorld(KeyWorlds keyWorlds){
        if (keyWorlds == null || keyWorlds.getName() == null){
            throw new NotFoundException("Veuillez remplir correctement tous les champs");
        }
        return keyWorldsRepo.save(keyWorlds);
    }

    public List<KeyWorlds> getAllKeyWorlds(){
        if (keyWorldsRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return keyWorldsRepo.findAll();
    }

    public KeyWorlds getByName(String name){
        KeyWorlds keyWorlds = keyWorldsRepo.findByName(name);
        if (keyWorlds == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee avec le label: "+ name);
        }
        return keyWorlds;
    }

    public KeyWorlds getById(Long id){
        KeyWorlds keyWorlds = keyWorldsRepo.findById(id).orElse(null);
        if (keyWorlds == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee avec l'id: "+ id);
        }
        return keyWorlds;
    }

    public KeyWorlds editKeyWorld(Long id, KeyWorlds keyWorlds){
        KeyWorlds key = keyWorldsRepo.findById(id).orElse(null);
        if (key == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee avec l'id: "+ id);
        }
        key.setActive(keyWorlds.getActive());
        key.setName(keyWorlds.getName());
        return keyWorldsRepo.save(key);
    }

    public String dropKeyWorld(Long id){
        KeyWorlds key = keyWorldsRepo.findById(id).orElse(null);
        if (key == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee avec l'id: "+ id);
        }
        key.setActive(false);
        keyWorldsRepo.save(key);
        return "Desactivation reussi";
    }
}
