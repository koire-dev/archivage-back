package com.api.archmemoire.services;

import com.api.archmemoire.entities.Departement;
import com.api.archmemoire.entities.Option;
import com.api.archmemoire.exceptions.NotFoundException;
import com.api.archmemoire.repositories.OptionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionService {

    private OptionRepo optionRepo;
    private DepartementService departementService;

    @Autowired
    public OptionService(OptionRepo optionRepo, DepartementService departementService) {
        this.optionRepo = optionRepo;
        this.departementService = departementService;
    }

    public Option getById(Long id){
        Option option = optionRepo.findById(id).orElse(null);
        if (option == null){
            throw new NotFoundException("Aucun resultat avec l'id: "+ id);
        }
        return option;
    }

    public Option addOption(Option option) {
        if (option == null){
            throw new NotFoundException("Remplissez tous les champs necessaires");
        }
        return optionRepo.save(option);
    }

    public List<Option> getAll() {
        List<Option> list = optionRepo.findAll();
        if (list == null){
            throw new NotFoundException("Aucun enregistrement dans la base de donnees");
        }
        return list;
    }

    public List<Option> getListOptionByDepartement(String codeDepart) {
        Departement departement = departementService.getByCode(codeDepart);
        if (departement == null){
            throw new NotFoundException("Aucun resultat avec le code: "+ codeDepart);
        }
        List<Option> optionList = optionRepo.findAllByDepartement(departement);
        if (optionList == null){
            throw new NotFoundException("Aucun resultat avec le code: "+ codeDepart);
        }
        return optionList;

    }

    public Option update(Long id, Option option) {
        Option update = optionRepo.findById(id).orElse(null);
        if (update == null){
            throw new NotFoundException("Aucun resultat avec l'id: "+ id);
        }
        update.setCode(option.getCode());
        update.setDescriptionFrench(option.getDescriptionFrench());
        update.setDescriptionEnglish(option.getDescriptionEnglish());

        return optionRepo.save(update);
    }

    public String delete(Long id) {
        Option option = getById(id);
        if (option == null){
            throw new NotFoundException("Aucun resultat avec l'id: "+ id);
        }
        option.setActive(false);
        optionRepo.save(option);

        return "Operation reussi avec succes";
    }

    public Option getByCode(String code) {
        Option option = optionRepo.findByCode(code).orElse(null);
        if (option == null){
            throw new NotFoundException("Aucun resultat avec le code: "+ code);
        }
        return option;
    }
}
