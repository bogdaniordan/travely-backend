package com.codecool.travely.repository;

import com.codecool.travely.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
}
