package com.codecool.travely.controller;

import com.amazonaws.services.dynamodbv2.xspec.L;
import com.codecool.travely.enums.Facility;
import com.codecool.travely.model.Accommodation;
import com.codecool.travely.service.AccommodationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/accommodations")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('CUSTOMER')")
@AllArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @GetMapping("/{id}")
    public ResponseEntity<Accommodation> getById(@PathVariable Long id) {
        return new ResponseEntity<>(accommodationService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Accommodation>> filterByLocation(@PathVariable String location) {
        return new ResponseEntity<>(accommodationService.filterByLocation(location), HttpStatus.OK);
    }

    @GetMapping("/place-type/{type}")
    public ResponseEntity<List<Accommodation>> filterByPlaceType(@PathVariable String type) {
        return new ResponseEntity<>(accommodationService.filterByPlaceType(type), HttpStatus.OK);
    }

    @GetMapping("/place-type/{type}/location/{location}")
    public ResponseEntity<List<Accommodation>> filterByLocationAndPlaceType(@PathVariable String type, @PathVariable String location) {
        return new ResponseEntity<>(accommodationService.filterByLocationAndType(location, type), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Accommodation>> getAll() {
        return new ResponseEntity<>(accommodationService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get-by-title/{titleInput}")
    public ResponseEntity<List<Accommodation>> getByTitleInput(@PathVariable String titleInput) {
        return new ResponseEntity<>(accommodationService.filterByAccommodationTitle(titleInput), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/all-for-host/{id}")
    public ResponseEntity<List<Accommodation>> getAllByHostId(@PathVariable Long id) {
        return new ResponseEntity<>(accommodationService.findAllByHostId(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")

    @GetMapping("/image/{accommodationId}/{imageName}/download")
    public byte[] downloadImage(@PathVariable Long accommodationId,
                                @PathVariable String imageName) {
        return accommodationService.downloadImage(accommodationId, imageName);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(
            path = "/image/upload/{accommodationId}/{imageName}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("accommodationId") Long accommodationId,
                                       @PathVariable("imageName") String imageName,
                                       @RequestParam("file") MultipartFile file) {
        accommodationService.uploadAccommodationPicture(accommodationId, file, imageName);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/all-facilities")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        return new ResponseEntity<>(accommodationService.getAllFacilities(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping("/add/{hostId}")
    public ResponseEntity<String> addAccommodation(@Valid @RequestBody Accommodation accommodation, @PathVariable Long hostId) {
        accommodationService.addNewAccommodation(accommodation, hostId);
        return ResponseEntity.ok("Accommodation has been saved.");
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping("/find-by-title/{title}")
    public ResponseEntity<Accommodation> findByTitle(@PathVariable String title) {
        return new ResponseEntity<>(accommodationService.findByTitle(title), HttpStatus.OK);
    }

    @GetMapping("/save-to-favorites/accommodation/{accommodationId}/customer/{customerId}")
    public ResponseEntity<String> saveToFavorites(@PathVariable Long accommodationId, @PathVariable Long customerId) {
        accommodationService.addAccommodationToFavorites(accommodationId, customerId);
        return ResponseEntity.accepted().body("Accommodation added to customer list.");
    }

    @GetMapping("/remove-from-favorites/accommodation/{accommodationId}/customer/{customerId}")
    public ResponseEntity<String> removeFromFavorites(@PathVariable Long accommodationId, @PathVariable Long customerId) {
        accommodationService.removeAccommodationFromFavorites(accommodationId, customerId);
        return ResponseEntity.accepted().body("Accommodation has been removed from the customer list.");
    }

    @GetMapping("/accommodation-is-saved/{accommodationId}/{customerId}")
    public ResponseEntity<Boolean> accommodationIsSaved(@PathVariable Long accommodationId, @PathVariable Long customerId) {
        return ResponseEntity.ok(accommodationService.favoritesContainsAccommodation(accommodationId, customerId));
    }

    @GetMapping("/all-saved/{customerId}")
    public ResponseEntity<List<Accommodation>> findAllSaved(@PathVariable Long customerId) {
        return ResponseEntity.ok(accommodationService.findAllSavedAccommodations(customerId));
    }
}
