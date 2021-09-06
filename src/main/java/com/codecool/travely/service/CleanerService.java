package com.codecool.travely.service;

import com.codecool.travely.model.Cleaner;
import com.codecool.travely.repository.CleanerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CleanerService {

    private final CleanerRepository cleanerRepository;
    private final HostService hostService;
    private final AccommodationService accommodationService;


    public List<Cleaner> findAll() {
        return cleanerRepository.findAll();
    }

    public void save(Cleaner cleaner) {
        cleanerRepository.save(cleaner);
    }

    public Cleaner findById(Long id) {
        return cleanerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find cleaner with id: " + id));
    }

    public void hireCleaner(Long cleanerId, Long hostId) {
        log.info("Hiring cleaner with id: " + cleanerId);
        Cleaner cleaner = findById(cleanerId);
        cleaner.setEmployer(hostService.findById(hostId));
        save(cleaner);
    }

    public void fireCleaner(Long cleanerId) {
        log.info("Firing cleaner with id: " + cleanerId);
        Cleaner cleaner = findById(cleanerId);
        cleaner.setEmployer(null);
        save(cleaner);
    }

    public void cleanAccommodation(Long cleanerId, Long accommodationId) {
        log.info("Cleaner with id " + cleanerId + " is now cleaning accommodation with id " + accommodationId);
        Cleaner cleaner = findById(cleanerId);
        cleaner.setCurrentCleaningJob(accommodationService.findById(accommodationId));
        save(cleaner);
    }
}
