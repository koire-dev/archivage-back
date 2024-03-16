package com.api.archmemoire.controllers;
import com.api.archmemoire.entities.Inscription;
import com.api.archmemoire.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/inscription")
public class InscriptionController {

    private InscriptionService inscriptionService;

    @Autowired
    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }


    //  Ajouter un Inscription
    @PostMapping("/addInscription")
    public ResponseEntity<Inscription> saveInscription(@RequestBody Inscription inscription){
        return new ResponseEntity<>(inscriptionService.addInscription(inscription), HttpStatus.OK);
    }

    //    Un inscription par id
    @GetMapping("/findInscriptionById/{id}")
    public ResponseEntity<Inscription> getInscriptionById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(inscriptionService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/updateInscription/{id}")
    public ResponseEntity<Inscription> editInscription(@PathVariable Long id, @RequestBody Inscription inscription){
        return new ResponseEntity<>(inscriptionService.update(id, inscription), HttpStatus.OK);
    }

    //    Supprimer une Inscription
    @PutMapping("/deleteInscription/{id}")
    public ResponseEntity<String> deleteInscription(@PathVariable("id") Long id){
        return new ResponseEntity<>(inscriptionService.delete(id), HttpStatus.OK);
    }
}
