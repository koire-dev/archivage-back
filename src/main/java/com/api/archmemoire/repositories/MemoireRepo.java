package com.api.archmemoire.repositories;

import com.api.archmemoire.entities.Memoire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoireRepo extends JpaRepository<Memoire, Long> {
}
