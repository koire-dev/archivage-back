package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepo extends JpaRepository<Categorie, Long> {
    Categorie findByLabel(String label);
}
