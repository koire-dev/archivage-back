package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DepartementRepo extends JpaRepository<Departement, Long>{

    Optional<Departement> findByCode(String code);

    Departement findByCodeAndActive(String code, boolean b);
}
