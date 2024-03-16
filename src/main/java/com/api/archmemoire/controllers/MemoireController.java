package com.api.archmemoire.controllers;

import com.api.archmemoire.entities.Memoire;
import com.api.archmemoire.services.MemoireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/memoire")
@CrossOrigin("*")
public class MemoireController {

    private MemoireService memoireService;

    @Autowired
    public MemoireController(MemoireService memoireService) {
        this.memoireService = memoireService;
    }

    @PostMapping("/add")
    public ResponseEntity<Memoire> addMemoire(@RequestBody Memoire memoire){
        return new ResponseEntity<>(memoireService.saveMemoire(memoire), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Memoire>> getAllMemoire(){
        return new ResponseEntity<>(memoireService.getAllMemoire(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Memoire> getMemoireById(@PathVariable Long id){
        return new ResponseEntity<>(memoireService.getMemoireById(id), HttpStatus.OK);
    }

    @GetMapping("/findByKeyworld")
    public ResponseEntity<List<Memoire>> getListMemoireByKeyworld(@RequestParam String keyworld){
        return new ResponseEntity<>(memoireService.getListMemoireByKeyWorlds(keyworld), HttpStatus.OK);
    }

    @GetMapping("/findByCategorie")
    public ResponseEntity<List<Memoire>> getMemoireByCategorie(@RequestParam String name){
        return new ResponseEntity<>(memoireService.getListMemoireByCategorie(name), HttpStatus.OK);
    }

    @GetMapping("/findByEtudiant")
    public ResponseEntity<List<Memoire>> getMemoireByEtudiant(@RequestParam String matricule){
        return new ResponseEntity<>(memoireService.getListMemoireByEtudiant(matricule), HttpStatus.OK);
    }

    @GetMapping("/findByParcours")
    public ResponseEntity<List<Memoire>> getMemoireByParcours(@RequestParam String label, @RequestParam String anneeAca){
        return new ResponseEntity<>(memoireService.getListMemoireByParcours(label, anneeAca), HttpStatus.OK);
    }

    @GetMapping("/findByDepartement")
    public ResponseEntity<List<Memoire>> getMemoireByDepartement(@RequestParam String code, @RequestParam String anneeAca){
        return new ResponseEntity<>(memoireService.getListMemoireByDepartement(code, anneeAca), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Memoire> updateMemoire(@PathVariable Long id, @RequestBody Memoire memoire){
        return new ResponseEntity<>(memoireService.editMemoire(id, memoire), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteMemoire(@PathVariable Long id){
        return new ResponseEntity<>(memoireService.dropMemoire(id), HttpStatus.OK);
    }
}
