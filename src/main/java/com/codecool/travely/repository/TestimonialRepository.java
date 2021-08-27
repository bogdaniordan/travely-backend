package com.codecool.travely.repository;

import com.codecool.travely.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial, Long> {

    List<Testimonial> findAllByAccommodationId(Long id);
}
