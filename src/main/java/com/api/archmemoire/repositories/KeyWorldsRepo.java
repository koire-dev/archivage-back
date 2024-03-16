package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.KeyWorlds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeyWorldsRepo extends JpaRepository<KeyWorlds, Long> {
    KeyWorlds findByName(String keyWorld);
}
