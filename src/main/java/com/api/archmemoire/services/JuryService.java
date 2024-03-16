package com.api.archmemoire.services;

import com.api.archmemoire.entities.Jury;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.JuryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JuryService {

    private JuryRepo juryRepo;

    @Autowired
    public JuryService(JuryRepo juryRepo) {
        this.juryRepo = juryRepo;
    }

    public Jury saveJury(Jury jury){
        if (jury == null){
            throw new NotFoundException("Veuillez remplir correctement tous les champs");
        }

        return juryRepo.save(jury);
    }

    public Jury getJuryById(Long id){
        Jury jury = juryRepo.findById(id).orElse(null);
        if (jury == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        return jury;
    }

    public List<Jury> getAllJury(){
        if (juryRepo.findAll().isEmpty()){
            throw new NotFoundException("Aucun enregistrement dans la base de donnee");
        }
        return juryRepo.findAll();
    }

    public Jury editJury(Long id, Jury jury){
        Jury result = juryRepo.findById(id).orElse(null);
        if (result == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        result.setActive(jury.getActive());
        result.setGrade(jury.getGrade());
        result.setName(jury.getName());
        result.setStatut(jury.getStatut());

        return juryRepo.save(result);
    }

    public String dropJury(Long id){
        Jury result = juryRepo.findById(id).orElse(null);
        if (result == null){
            throw new NotFoundException("Aucun objet trouve avec l'id: "+ id);
        }
        result.setActive(false);
        juryRepo.save(result);
        return "Desactivation effectuee avec succes";
    }
}
