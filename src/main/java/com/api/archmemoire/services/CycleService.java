package com.api.archmemoire.services;

import com.api.archmemoire.entities.Cycle;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.CycleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CycleService {

    private CycleRepo cycleRepo;

    @Autowired
    public CycleService(CycleRepo cycleRepo) {
        this.cycleRepo = cycleRepo;
    }

    public List<Cycle> getAll() {
        List<Cycle> list = cycleRepo.findAll();
        if (list == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return list;
    }

    public Cycle addCycle(Cycle cycle) {
        if (cycle == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        return cycleRepo.save(cycle);
    }

    public Cycle getById(Long id) {
        Cycle cycle = cycleRepo.findById(id).orElse(null);
        if (cycle == null){
            throw new NotFoundException("Aucun objet pour l'id : "+id + " dans la base de donnee");
        }
        return cycle;
    }

    public Cycle updateById(Long id, Cycle cycle) {
        Cycle update = cycleRepo.findById(id).orElse(null);
        if (update == null){
            throw new NotFoundException("Aucun objet pour l'id : "+id + " dans la base de donnee");
        }
        update.setValeur(cycle.getValeur());
        return cycleRepo.save(update);
    }

    public String delete(Long id) {
        Cycle cycle = getById(id);
        if (cycle == null){
            throw new NotFoundException("Aucun objet pour l'id : "+id + " dans la base de donnee");
        }
        cycle.setActive(false);
        cycleRepo.save(cycle);

        return "Operation reussi avec succes";
    }

    public Cycle getByValeur(int value){
        if (cycleRepo.findByValeur(value) == null){
            throw new NotFoundException("Aucun objet pour la valeur : "+value + " dans la base de donnee");
        }
        return cycleRepo.findByValeur(value);
    }
}
