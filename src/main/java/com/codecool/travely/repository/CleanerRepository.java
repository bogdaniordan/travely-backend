package com.codecool.travely.repository;

import com.codecool.travely.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long> {
    List<Cleaner> findAllByCurrentCleaningJobId(Long id);
    List<Cleaner> findAllByEmployerId(Long id);
}
