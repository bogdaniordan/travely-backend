package com.codecool.travely.service;

import com.codecool.travely.enums.CleaningStatus;
import com.codecool.travely.exception.customs.WrongStatusException;
import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.Cleaner;
import com.codecool.travely.repository.CleanerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CleanerService {

    private final CleanerRepository cleanerRepository;
    private final AccommodationService accommodationService;
    private final HostService hostService;

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
        cleaner.setHired(true);
        cleaner.setCleaningStartDate(null);
        save(cleaner);
    }

    public void fireCleaner(Long cleanerId) {
        log.info("Firing cleaner with id: " + cleanerId);
        Cleaner cleaner = findById(cleanerId);
        cleaner.setEmployer(null);
        cleaner.setHired(false);
        cleaner.setCurrentCleaningJob(null);
        cleaner.setCleaningStartDate(null);
        save(cleaner);
    }

    public List<Cleaner> filterByHiringStatus(String status) {
        if (status == null) {
           throw new WrongStatusException("Status not found.");
        }
        if (status.equals("Free")) {
            return findAll().stream().filter(cleaner -> !cleaner.isHired()).collect(Collectors.toList());
        } else if (status.equals("Hired")) {
            return findAll().stream().filter(Cleaner::isHired).collect(Collectors.toList());
        }
        return findAll();
    }

    public List<Cleaner> getAllCleanersForHost(long hostId) {
        log.info("Fetching all cleaners for host with id: " + hostId);
        return findAll().stream()
                .filter(cleaner -> cleaner.getEmployer() != null)
                .filter(cleaner -> cleaner.getEmployer().getId() == hostId)
                .collect(Collectors.toList());
    }

    public Boolean accommodationCanBeCleaned(Long id) {
        log.info("Checking if accommodation with id " + id + " can be cleaned.");
        Accommodation accommodation = accommodationService.findById(id);
        if (accommodation.getCleaningStatus() == CleaningStatus.CLEAN) {
            return false;
        }
        return findAll().stream()
                .filter(cleaner -> cleaner.getCurrentCleaningJob() != null)
                .noneMatch(cleaner -> cleaner.getCurrentCleaningJob().equals(accommodation));
    }

    public void setCleanerToCleanAccommodation(Long cleanerId, Long accommodationId) {
        log.info("Set cleaner with id " + cleanerId + " to clean accommodation with id  " + accommodationId);
        Cleaner cleaner = findById(cleanerId);
        cleaner.setCurrentCleaningJob(accommodationService.findById(accommodationId));
        cleaner.addToCleaningHistory(accommodationService.findById(accommodationId));
        cleaner.setCleaningStartDate(LocalDate.now());
        save(cleaner);
    }

    public List<Cleaner> accommodationIsCleanedBy(long accommodationId) {
        return findAll().stream().filter(cleaner -> cleaner.getCurrentCleaningJob() != null).filter(cleaner -> cleaner.getCurrentCleaningJob().getId() == accommodationId).collect(Collectors.toList());
    }



    public boolean setAccommodationToCleaned(Long accommodationId) {
        log.info("Checking if accommodation has been cleaned.");
        Accommodation accommodation = accommodationService.findById(accommodationId);
        if (accommodationIsCleanedBy(accommodationId).size() == 1) {
            Cleaner cleaner = accommodationIsCleanedBy(accommodationId).get(0);
            if (cleaner.getCleaningStartDate().plusDays(cleaner.getExperience().getCleaningDurationInDays()).compareTo(LocalDate.now()) == 0) {
                accommodation.setCleaningStatus(CleaningStatus.CLEAN);
                cleaner.addToCleaningHistory(accommodation);
                cleaner.setCurrentCleaningJob(null);
                cleaner.setCleaningStartDate(null);
                cleanerRepository.save(cleaner);
                accommodationService.saveAccommodation(accommodation);
                return true;
            }
        }
        return false;
    }
}
