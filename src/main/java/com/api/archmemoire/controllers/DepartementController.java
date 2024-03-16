package com.api.archmemoire.controllers;

import java.util.List;

import com.api.archmemoire.entities.Departement;
import com.api.archmemoire.services.DepartementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/departement")
public class DepartementController {

    private DepartementService departementService;

    @Autowired
    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @PostMapping("/addDepartement")
    public ResponseEntity<Departement> saveDepartement(@RequestBody Departement departement) {
        return new ResponseEntity<>(departementService.addDepartement(departement), HttpStatus.OK);
    }

    @GetMapping("/findAllDepartement")
    public ResponseEntity<List<Departement>> getDepartement() {
        return new ResponseEntity<>(departementService.getAll(), HttpStatus.OK);
    }

//    Departement par son code
    @GetMapping("/findDepartByCode/departement")
    public ResponseEntity<Departement> getDepartementByCode(@RequestParam String code){
        return new ResponseEntity<>(departementService.getByCode(code), HttpStatus.OK);
    }

    @GetMapping("/findDepartById/{id}")
    public ResponseEntity<Departement> getDepartById(@PathVariable("id") Long id){
        return new ResponseEntity<>(departementService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/updateDepart/{id}")
    public ResponseEntity<Departement> updateDepart(@PathVariable("id") Long id, @RequestBody Departement departement){
        return new ResponseEntity<>(departementService.updateById(id, departement), HttpStatus.OK);
    }

    @PutMapping("/deleteDepart/{id}")
    public ResponseEntity<String> deleteNiveau(@RequestBody @PathVariable Long id) {
        return new ResponseEntity<>(departementService.delete(id), HttpStatus.OK);
    }
}
