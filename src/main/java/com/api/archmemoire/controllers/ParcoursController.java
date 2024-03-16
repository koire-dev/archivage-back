package com.api.archmemoire.controllers;
import com.api.archmemoire.entities.Parcours;
import com.api.archmemoire.services.ParcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/parcours")
public class ParcoursController {

   private ParcoursService parcoursService;

    @Autowired
    public ParcoursController(ParcoursService parcoursService) {
        this.parcoursService = parcoursService;
    }

    //  Ajouter un parcours
    @PostMapping("/addParcours")
    public ResponseEntity<Parcours> saveParcours(@RequestBody Parcours parcours){
        return new ResponseEntity<>(parcoursService.addParcours(parcours), HttpStatus.OK);
    }

    //    Liste des parcours
    @GetMapping("/findAllParcours")
    public ResponseEntity<List<Parcours>> getAllParcours() {
        return new ResponseEntity<>(parcoursService.getAll(), HttpStatus.OK);
    }

    //    Un parours par id
    @GetMapping("/findParcoursById/{id}")
    public ResponseEntity<Parcours> getParcoursById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(parcoursService.getById(id), HttpStatus.OK);
    }

//    Parcours par niveau et option
    @GetMapping("/findParcoursByNiveauAndOption/niveau/{value}/option")
    public ResponseEntity<Parcours> getParcoursByNiveauAndOption(@PathVariable int value, @RequestParam("code") String code){
        return new ResponseEntity<>(parcoursService.getParcoursByOptionAndNiveau(value, code), HttpStatus.OK);
    }

//    Liste des parcours pour un departement
    @GetMapping("/findParcoursByDepart")
    public ResponseEntity<List<Parcours>> getParcoursByDepart(@RequestParam String code){
        return new ResponseEntity<>(parcoursService.getAllByDepartement(code), HttpStatus.OK);
    }

    //    Supprimer un parcours
    @PutMapping("/deleteParcours/{id}")
    public ResponseEntity<String> deleteParcours(@PathVariable("id") Long id){
        return new ResponseEntity<>(parcoursService.delete(id), HttpStatus.OK);
    }
}
