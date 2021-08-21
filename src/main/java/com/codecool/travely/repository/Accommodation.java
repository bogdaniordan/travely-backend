package com.codecool.travely.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Accommodation extends JpaRepository<Accommodation, Long> {
}
