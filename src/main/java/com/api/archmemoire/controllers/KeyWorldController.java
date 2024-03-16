package com.api.archmemoire.controllers;

import com.api.archmemoire.entities.KeyWorlds;
import com.api.archmemoire.services.KeyWorldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/keyworld")
@CrossOrigin("*")
public class KeyWorldController {

    private KeyWorldsService keyWorldsService;

    @Autowired
    public KeyWorldController(KeyWorldsService keyWorldsService) {
        this.keyWorldsService = keyWorldsService;
    }

    @PostMapping("/add")
    public ResponseEntity<KeyWorlds> addKeyWorld(@RequestBody KeyWorlds keyWorlds){
        return new ResponseEntity<>(keyWorldsService.saveKeyWorld(keyWorlds), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<KeyWorlds>> getAllKeyWorld(){
        return new ResponseEntity<>(keyWorldsService.getAllKeyWorlds(), HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<KeyWorlds> getKeyWorldById(@PathVariable Long id){
        return new ResponseEntity<>(keyWorldsService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/findByName")
    public ResponseEntity<KeyWorlds> getKeyWorldByName(@RequestParam String name){
        return new ResponseEntity<>(keyWorldsService.getByName(name), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<KeyWorlds> updateKeyWorld(@PathVariable Long id, @RequestBody KeyWorlds keyWorlds){
        return new ResponseEntity<>(keyWorldsService.editKeyWorld(id, keyWorlds), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<String> deleteKeyWorld(@PathVariable Long id){
        return new ResponseEntity<>(keyWorldsService.dropKeyWorld(id), HttpStatus.OK);
    }
}
