package com.api.archmemoire.controllers;

import com.api.archmemoire.entities.Categorie;
import com.api.archmemoire.services.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorie")
@CrossOrigin("*")
public class CategorieController {

    private CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping("/add")
    public ResponseEntity<Categorie> addCategorie(@RequestBody Categorie categorie){
        return new ResponseEntity<>(categorieService.saveCategorie(categorie), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Categorie>> getAllCategorie(){
        return new ResponseEntity<>(categorieService.getAllCategorie(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Categorie> getByIdCategorie(@PathVariable Long id){
        return new ResponseEntity<>(categorieService.getCategorieById(id), HttpStatus.OK);
    }

    @GetMapping("/findByLabel")
    public ResponseEntity<Categorie> getByLabelCategorie(@RequestParam String label){
        return new ResponseEntity<>(categorieService.getCategorieByLabel(label), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorie){
        return new ResponseEntity<>(categorieService.editCategorie(id, categorie), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable Long id){
        return new ResponseEntity<>(categorieService.dropCategorie(id), HttpStatus.OK);
    }
}
