package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.enums.AccommodationStatus;
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
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final CustomerService customerService;
    private final FileChecker fileChecker;
    private final FileStore fileStore;

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

    public void saveAccommodationToCustomerList(Long accommodationId, Long customerId) {
        Customer customer = customerService.findById(customerId);
        customer.saveAccommodation(findById(accommodationId));
        customerService.saveCustomer(customer);
    }

    public List<Accommodation> findAllByHostId(Long id) {
        log.info("Fetching all accommodations for host with id: " + id);
        return accommodationRepository.findAllByHostId(id);
    }

    public byte[] downloadImage(Long id, String imageName) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        String imageUrl = null;
        switch (imageName) {
            case "firstImage":
                imageUrl = findById(id).getImageUrls().getFirstImage();
                break;
            case "secondImage":
                imageUrl = findById(id).getImageUrls().getSecondImage();
                break;
            case "thirdImage":
                imageUrl = findById(id).getImageUrls().getThirdImage();
                break;
        }
        return fileStore.download(path, imageUrl);
    }

    public void uploadAccommodationPicture(Long accommodationId, MultipartFile file, String imageName) {
        log.info("Uploading a picture for accommodation with id: " + accommodationId);
        Map<String, String> metadata = fileChecker.checkFile(file);
        Accommodation accommodation = findById(accommodationId);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), accommodationId);
        try {
            fileStore.save(path, imageName, Optional.of(metadata), file.getInputStream());
            switch (imageName) {
                case "firstImage":
                    accommodation.getImageUrls().setFirstImage("firstImage");
                    break;
                case "secondImage":
                    accommodation.getImageUrls().setSecondImage("secondImage");
                    break;
                case "thirdImage":
                    accommodation.getImageUrls().setThirdImage("thirdImage");
                    break;
            }
            saveAccommodation(accommodation);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }
}
