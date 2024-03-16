package com.api.archmemoire.services;

import com.api.archmemoire.entities.Niveau;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.NiveauRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauService {

    private NiveauRepo niveauRepo;
    private ParcoursService parcoursService;

    @Autowired
    public NiveauService(NiveauRepo niveauRepo, ParcoursService parcoursService) {
        this.niveauRepo = niveauRepo;
        this.parcoursService = parcoursService;
    }

    public Niveau addNiveau(Niveau niveau) {
        if (niveau == null){
            throw new NotFoundException("Veuillez remplir correctement les champs");
        }
        Boolean terminal = false;
        if (niveau.getValeur().equals(3) || niveau.getValeur().equals(5)){
            terminal = true;
        }

        Niveau newNiveau = new Niveau();
        newNiveau.setCycle(niveau.getCycle());
        newNiveau.setValeur(niveau.getValeur());

        return niveauRepo.save(newNiveau);
    }

    public List<Niveau> getAll() {
        List<Niveau> niveauList = niveauRepo.findAll();
        if (niveauList == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return niveauList;
    }

    public Niveau getById(Long id) {
        Niveau niveau = niveauRepo.findById(id).orElse(null);
        if (niveau == null){
            throw new NotFoundException("Aucun objet niveau avec l'id : "+id + " dans la base de donnee");
        }
        return niveau;
    }

    public Niveau update(Long id, Niveau niveau) {
        Niveau niveauFromDb = getById(id);
        if (niveauFromDb == null){
            throw new NotFoundException("Aucun objet niveau avec l'id : "+id + " dans la base de donnee");
        }
        niveauFromDb.setValeur(niveau.getValeur());
        niveauFromDb.setCycle(niveau.getCycle());

        return niveauRepo.save(niveauFromDb);
    }

    public String delete(Long id) {
        Niveau niveau = getById(id);
        if (niveau == null){
            throw new NotFoundException("Aucun objet niveau avec l'id : "+id + " dans la base de donnee");
        }
        niveau.setActive(false);
        niveauRepo.save(niveau);

        return "Operation reussi avec succes";
    }

    public Niveau getByValeur(int level) {
        Niveau niveau = niveauRepo.findByValeur(level).orElse(null);
        if (niveau == null){
            throw new NotFoundException("Aucun objet niveau avec la valeur : "+level + " dans la base de donnee");
        }
        return niveau;
    }
}
