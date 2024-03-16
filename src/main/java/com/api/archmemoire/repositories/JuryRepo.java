package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Jury;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuryRepo extends JpaRepository<Jury, Long> {
}
