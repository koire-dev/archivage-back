package com.api.archmemoire.controllers;

import com.api.archmemoire.entities.Jury;
import com.api.archmemoire.services.JuryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jury")
@CrossOrigin("*")
public class JuryController {

    private JuryService juryService;

    @Autowired
    public JuryController(JuryService juryService) {
        this.juryService = juryService;
    }

    @PostMapping("/add")
    public ResponseEntity<Jury> addJury(@RequestBody Jury jury){
        return new ResponseEntity<>(juryService.saveJury(jury), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Jury>> getAllJury(){
        return new ResponseEntity<>(juryService.getAllJury(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Jury> getJuryById(@PathVariable Long id){
        return new ResponseEntity<>(juryService.getJuryById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Jury> updateJury(@PathVariable Long id, @RequestBody Jury jury){
        return new ResponseEntity<>(juryService.editJury(id, jury), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteJury(@PathVariable Long id){
        return new ResponseEntity<>(juryService.dropJury(id), HttpStatus.OK);
    }
}
