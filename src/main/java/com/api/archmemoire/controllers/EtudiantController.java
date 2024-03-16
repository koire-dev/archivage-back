package com.api.archmemoire.controllers;
import com.api.archmemoire.entities.Etudiant;
import com.api.archmemoire.services.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/etudiant")
public class EtudiantController {

    private EtudiantService etudiantService;

    @Autowired
    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    //  Ajouter un etudiant
    @PostMapping("/addEtudiant")
    public ResponseEntity<Etudiant> saveEtudiant(@RequestBody Etudiant etudiant){
        return new ResponseEntity<>(etudiantService.addEtudiant(etudiant), HttpStatus.OK);
    }

    //    Liste des etudiant
    @GetMapping("/findAllEtudiant")
    public ResponseEntity<List<Etudiant>> getAllEtudiant() {
        return new ResponseEntity<>(etudiantService.getAll(), HttpStatus.OK);
    }

    //    Un etudiant par id
    @GetMapping("/findEtudiantById/{id}")
    public ResponseEntity<Etudiant> getEtudiantById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(etudiantService.getById(id), HttpStatus.OK);
    }

    //    Un etudiant par le matricule de l'etudiant
    @GetMapping("/findEtudiantByMatricule")
    public ResponseEntity<Etudiant> getEtudiantByMatricule(@RequestParam String matricule){
        return new ResponseEntity<>(etudiantService.getByMatricule(matricule), HttpStatus.OK);
    }

    //    Liste des Etudiants d'un departement
    @GetMapping("/findAllEtudiantByDepart")
    public ResponseEntity<List<Etudiant>> getAllEtudiantByDepart(@RequestParam("code") String code){
        return new ResponseEntity<>(etudiantService.getEtudiantByDepartement(code), HttpStatus.OK);
    }

//    liste des etudiants d'un parcours
    @GetMapping("/findAllEtudiantByParcours/annee/{year}/parcours")
    public ResponseEntity<List<Etudiant>> getAllEtudiantByParcours(@PathVariable int year, @RequestParam String label){
        return new ResponseEntity<>(etudiantService.getListEtudiantByParcours(label, year), HttpStatus.OK);
    }

    //    Modifier un Etudiant
    @PutMapping("/updateEtudiant/{id}")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable("id") Long id, @RequestBody Etudiant etudiant){
        return new ResponseEntity<>(etudiantService.updateById(id, etudiant), HttpStatus.OK);
    }

    //    Supprimer un Etudiant
    @DeleteMapping("/deleteEtudiant/{id}")
    public ResponseEntity<String> deleteEtudiant(@PathVariable("id") Long id){
        return new ResponseEntity<>(etudiantService.delete(id), HttpStatus.OK);
    }
}
