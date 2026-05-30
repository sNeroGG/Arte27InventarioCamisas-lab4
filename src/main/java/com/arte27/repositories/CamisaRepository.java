package com.arte27.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.arte27.models.Camisa;

@Repository
public interface CamisaRepository extends JpaRepository<Camisa, Integer> {
}
