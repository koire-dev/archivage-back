package com.api.archmemoire.services;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MatriculeService{

    public String matriculeGenerator(Long id) {

        LocalDate date = LocalDate.now();
        int year = date.getYear();
        int numYear = year - 2000;

        if (Long.toString(id).length() == 1){
            return numYear + "Y00" + id + "EB";
        }else if (Long.toString(id).length() == 2){
            return numYear + "Y0" + id + "EB";
        }else {
            return numYear + "Y" + id + "EB";
        }
    }
}
