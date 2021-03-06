package com.codecool.travely.repository;

import com.codecool.travely.model.user.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Host findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
