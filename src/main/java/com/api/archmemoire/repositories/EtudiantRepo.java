package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * @author COMPUTER STORES
 */
public interface EtudiantRepo extends JpaRepository<Etudiant, Long> {
    
    Optional<Etudiant> findByMatricule(String matricule);
}
