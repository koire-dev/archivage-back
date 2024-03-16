package com.api.archmemoire.controllers;


import java.util.List;

import com.api.archmemoire.entities.AnneeAcademique;
import com.api.archmemoire.services.AnneeAcademiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/anneeAcademique")
public class AnneeAcademiqueController {

    private AnneeAcademiqueService anneeAcademiqueService;

    @Autowired
    public AnneeAcademiqueController(AnneeAcademiqueService anneeAcademiqueService) {
        this.anneeAcademiqueService = anneeAcademiqueService;
    }

    //    Liste des annees academiques
    @GetMapping("/findAllAnneeAcademique")
    public ResponseEntity<List<AnneeAcademique>> getAnnee() {
        return new ResponseEntity<>(anneeAcademiqueService.getAll(), HttpStatus.OK);
    }

//    ajouter une annee academique
    @PostMapping("/addAnneeAcademique")
    public ResponseEntity<AnneeAcademique> saveAnnee(@RequestBody AnneeAcademique anneeAcademique) {
        return new ResponseEntity<>(anneeAcademiqueService.addAnneeAcademique(anneeAcademique), HttpStatus.OK);
    }

//    Une annee par son id
    @GetMapping("/findAnneeAcaById/{id}")
    public ResponseEntity<AnneeAcademique> getAnneeAcaById(@PathVariable Long id){
        return new ResponseEntity<>(anneeAcademiqueService.getById(id), HttpStatus.OK);
    }

//    Modifier une annee academique
    @PutMapping("/uptadeAnneeAca/{id}")
    public ResponseEntity<AnneeAcademique> updateAnnee(@PathVariable("id") Long id, @RequestBody AnneeAcademique anneeAcademique) {
        return new ResponseEntity<>(anneeAcademiqueService.updateById(id, anneeAcademique), HttpStatus.OK);
    }

//    Supprimer une annee academique
    @PutMapping("/deleteAnneeAca/{id}")
    public ResponseEntity<String> deleteAnnee(@PathVariable("id") Long id){
        return new ResponseEntity<>(anneeAcademiqueService.delete(id), HttpStatus.OK);
    }
}
