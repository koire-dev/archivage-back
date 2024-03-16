package com.api.archmemoire.controllers;
import com.api.archmemoire.entities.Option;
import com.api.archmemoire.services.OptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/option")
public class OptionController {

    private OptionService optionService;

    @Autowired
    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @PostMapping("/addOption")
    public ResponseEntity<Option> saveOption(@RequestBody Option option){
        return new ResponseEntity<>(optionService.addOption(option), HttpStatus.OK);
    }

    //    Liste des options
    @GetMapping("/findAllOptions")
    public ResponseEntity<List<Option>> getAllOptions() {
        return new ResponseEntity<>(optionService.getAll(), HttpStatus.OK);
    }

    //    Un option par id
    @GetMapping("/findOptionById/{id}")
    public ResponseEntity<Option> getOptionById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(optionService.getById(id), HttpStatus.OK);
    }

    //    Liste des options d'un departement
    @GetMapping("/findAllOptionByDepart")
    public ResponseEntity<List<Option>> getAllOptionByDepart(@RequestParam("code") String codeDepart){
        return new ResponseEntity<>(optionService.getListOptionByDepartement(codeDepart), HttpStatus.OK);
    }

    //    Modifier une option
    @PutMapping("/updateOption/{id}")
    public ResponseEntity<Option> updateOption(@PathVariable("id") Long id, @RequestBody Option option){
        return new ResponseEntity<>(optionService.update(id, option), HttpStatus.OK);
    }

    //    Supprimer une option
    @PutMapping("/deleteOption/{id}")
    public ResponseEntity<String> deleteOption(@PathVariable("id") Long id){
        return new ResponseEntity<>(optionService.delete(id), HttpStatus.OK);
    }
}
