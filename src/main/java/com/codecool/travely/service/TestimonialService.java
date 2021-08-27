package com.codecool.travely.service;

import com.codecool.travely.model.Testimonial;
import com.codecool.travely.repository.TestimonialRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class TestimonialService {

    private final TestimonialRepository testimonialRepository;

    public Testimonial findById(Long id) {
        return testimonialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial with id " + id + " not found."));
    }

    public void save(Testimonial testimonial) {
        testimonialRepository.save(testimonial);
    }

    public List<Testimonial> getAllByAccommodationId(Long id) {
        return testimonialRepository.findAllByAccommodationId(id);
    }
}
