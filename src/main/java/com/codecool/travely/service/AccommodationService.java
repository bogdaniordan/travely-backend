package com.codecool.travely.service;

import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.AccommodationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the accommodation with id: " + id));
    }

    public void saveAccommodation(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

    public List<Accommodation> filterByLocation(String location) {
        return findAll().stream().filter(ac -> ac.getCity().equals(location)).collect(Collectors.toList());
    }

}
