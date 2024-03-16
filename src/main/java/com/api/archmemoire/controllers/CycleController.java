package com.api.archmemoire.controllers;
import java.util.List;

import com.api.archmemoire.entities.Cycle;
import com.api.archmemoire.services.CycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin("*")
//@RequestMapping("/api/v1/admin/cycle")
public class CycleController {

    private CycleService cycleService;

    @Autowired
    public CycleController(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    @GetMapping("/findAllCycle")
    public ResponseEntity<List<Cycle>> getCycle() {
        return new ResponseEntity<>(cycleService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/addCycle")
    public ResponseEntity<Cycle> saveCycle(@RequestBody Cycle cycle) {
        return new ResponseEntity<>(cycleService.addCycle(cycle), HttpStatus.OK);
    }

    @GetMapping("/findCycleById/{id}")
    public ResponseEntity<Cycle> getCycleById(@PathVariable("id") Long id){
        return new ResponseEntity<>(cycleService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/updateCycle/{id}")
    public ResponseEntity<Cycle> updateCycle(@PathVariable("id") Long id, @RequestBody Cycle cycle){
        return new ResponseEntity<>(cycleService.updateById(id, cycle), HttpStatus.OK);
    }
    
    @PutMapping("/deleteCycle/{id}")
    public ResponseEntity<String> deleteCycle(@RequestBody @PathVariable Long id){
        return new ResponseEntity<>(cycleService.delete(id), HttpStatus.OK);
    }
}
