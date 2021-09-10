package com.codecool.travely.service;

import com.codecool.travely.model.Testimonial;
import com.codecool.travely.repository.TestimonialRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class TestimonialService {

    private final TestimonialRepository testimonialRepository;
    private final CustomerService customerService;
    private final AccommodationService accommodationService;

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

    public void delete(Long id) {
        log.info("Deleting testimonial with id: " + id);
        testimonialRepository.deleteById(id);
    }

    public void addTestimonial(Long accommodationId, Long customerId, Testimonial testimonial) {
        testimonial.setCustomer(customerService.findById(customerId));
        testimonial.setAccommodation(accommodationService.findById(accommodationId));
        save(testimonial);
    }

    public Boolean accommodationIsReviewed(long accommodationId, long customerId) {
        return testimonialRepository.findAll().stream().anyMatch(testimonial -> testimonial.getAccommodation().getId() == accommodationId && testimonial.getCustomer().getId() == customerId);
    }

    public Double getAverageRating(Long accommodationId) {
        log.info("Getting average rating for accommodation with id " + accommodationId);
        return getAllByAccommodationId(accommodationId).stream().mapToDouble(Testimonial::getRating).average().orElse(Double.NaN);
    }
}
