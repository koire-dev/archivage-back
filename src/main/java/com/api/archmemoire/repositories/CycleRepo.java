package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Cycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleRepo extends JpaRepository<Cycle, Long> {
    Cycle findByValeur(int cycle);
}

