
package com.api.archmemoire.controllers;

import java.util.List;

import com.api.archmemoire.entities.Niveau;
import com.api.archmemoire.services.NiveauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/niveau")
public class NiveauController {

    private NiveauService niveauService;

    @Autowired
    public NiveauController(NiveauService niveauService) {
        this.niveauService = niveauService;
    }


    @PostMapping("/addNiveau")
    public ResponseEntity<Niveau> saveNiveau(@RequestBody Niveau niveau){
        return new ResponseEntity<>(niveauService.addNiveau(niveau), HttpStatus.OK);
    }
    
    
    @GetMapping("/findAllNiveau")
    public ResponseEntity<List<Niveau>> getNiveau() {
        return new ResponseEntity<>(niveauService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/findNiveauById/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable("id") Long id){
        return new ResponseEntity<>(niveauService.getById(id), HttpStatus.OK);
    }
    
    @PutMapping("/uptadeNiveau/{id}")
    public ResponseEntity<Niveau> updateNiveau(@PathVariable("id") Long id, @RequestBody Niveau niveau) {
        return new ResponseEntity<>(niveauService.update(id, niveau), HttpStatus.OK);
    }
    
    @PutMapping("/deleteNiveau/{id}")
    public ResponseEntity<String> deleteNiveau(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(niveauService.delete(id), HttpStatus.OK);
    }
}

