package com.codecool.travely.repository;

import com.codecool.travely.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Customer findByUsername(String username);

    Long findIdByUsername(String username);

    Customer findByEmail(String email);
}
