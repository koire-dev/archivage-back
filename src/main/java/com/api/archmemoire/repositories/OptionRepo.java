package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Departement;
import com.api.archmemoire.entities.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OptionRepo extends JpaRepository<Option, Long> {
    Optional<Option> findByCode(String code);

    List<Option> findAllByDepartement(Departement departement);
}
