package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.enums.AccommodationStatus;
import com.codecool.travely.enums.CleaningStatus;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.model.Accommodation;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.AccommodationRepository;
import com.codecool.travely.util.FileChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final CustomerService customerService;
    private final FileChecker fileChecker;
    private final FileStore fileStore;
    private final HostService hostService;
//    private final CleanerService cleanerService;

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll().stream().filter(accommodation -> accommodation.getStatus() == AccommodationStatus.Free).collect(Collectors.toList());
    }

    public Accommodation findById(Long id) {
        return accommodationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the accommodation with id: " + id));
    }

    public void saveAccommodation(Accommodation accommodation) {
        accommodationRepository.save(accommodation);
    }

    public List<Accommodation> filterByLocation(String location) {
        log.info("Filtering accommodations by location: " + location);
        return findAll().stream().filter(ac -> ac.getLocation().equals(location) && ac.getStatus() == AccommodationStatus.Free).collect(Collectors.toList());
    }

    public List<Accommodation> filterByPlaceType(String placeType) {
        log.info("Filtering accommodations by place type: " + placeType);
        return findAll().stream().filter(accommodation -> accommodation.getPlaceType().toString().equals(placeType) && accommodation.getStatus() == AccommodationStatus.Free).collect(Collectors.toList());
    }

    public List<Accommodation> filterByLocationAndType(String location, String placeType) {
        log.info("Filtering places by " + location + " and " + placeType);
        return findAll().stream().filter(accommodation -> accommodation.getLocation().equals(location) && accommodation.getPlaceType().toString().equals(placeType) && accommodation.getStatus() == AccommodationStatus.Free).collect(Collectors.toList());
    }

    public List<Accommodation> filterByAccommodationTitle(String titleInput) {
        log.info("Fetching accommodations that contain " + titleInput + " in their title.");
        return findAll().stream().filter(accommodation -> accommodation.getTitle().toLowerCase().contains(titleInput.toLowerCase()) && accommodation.getStatus() == AccommodationStatus.Free).collect(Collectors.toList());
    }


    public List<Accommodation> findAllByHostId(Long id) {
        log.info("Fetching all accommodations for host with id: " + id);
        return accommodationRepository.findAllByHostId(id);
    }

    public byte[] downloadImage(Long id, String imageName) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        return fileStore.download(path, imageName + ".jpg");
    }

    public void uploadAccommodationPicture(Long accommodationId, MultipartFile file, String imageName) {
        log.info("Uploading a picture for accommodation with id: " + accommodationId);
        Map<String, String> metadata = fileChecker.checkFile(file);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), accommodationId);
        try {
            fileStore.save(path, imageName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public List<Facility> getAllFacilities() {
        return Arrays.asList(Facility.values());
    }

    public void addNewAccommodation(Accommodation accommodation, Long hostId) {
        log.info("Saving a new accommodation for host: " + hostId);
        accommodation.setHost(hostService.findById(hostId));
        saveAccommodation(accommodation);
    }

    public Accommodation findByTitle(String title) {
        return accommodationRepository.findByTitle(title);
    }

    public void addAccommodationToFavorites(Long accommodationId, Long customerId) {
        log.info("Saving accommodation to favorites accommodation with id: " + accommodationId);
        Customer customer = customerService.findById(customerId);
        customer.addToFavorites(findById(accommodationId));
        customerService.saveCustomer(customer);
    }

    public void removeAccommodationFromFavorites(Long accommodationId, Long customerId) {
        log.info("Removing from favorites - accommodation with id: " + accommodationId);
        Customer customer = customerService.findById(customerId);
        customer.removeFromFavorites(findById(accommodationId));
        customerService.saveCustomer(customer);
    }

    public Boolean favoritesContainsAccommodation(Long accommodationId, Long customerId) {
        log.info("Checking if accommodation with id of " + accommodationId + " is saved.");
        return customerService.findById(customerId).getSavedAccommodations().contains(findById(accommodationId));
    }

    public List<Accommodation> findAllSavedAccommodations(Long userId) {
        log.info("Fetching all saved accommodations");
        return customerService.findById(userId).getSavedAccommodations();
    }


}
