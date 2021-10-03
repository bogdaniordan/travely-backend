package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.dto.response.BadgeDto;
import com.codecool.travely.enums.Badge;
import com.codecool.travely.model.Host;
import com.codecool.travely.repository.AccommodationRepository;
import com.codecool.travely.repository.BookingRepository;
import com.codecool.travely.repository.CleanerRepository;
import com.codecool.travely.repository.HostRepository;
import com.codecool.travely.util.FileChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final FileStore fileStore;
    private final FileChecker fileChecker;
    private final AccommodationRepository accommodationRepository;
    private final BookingRepository bookingRepository;
    private final CleanerRepository cleanerRepository;

    public Host findById(Long id) {
        return hostRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the host with id: " + id));
    }

    public void saveHost(Host host) {
        hostRepository.save(host);
    }

    public Host findByUsername(String username) {
        return hostRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return hostRepository.existsByUsername(username);
    }

    public byte[] downloadImage(Long id) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        String imageURL = findById(id).getPicture();
        return fileStore.download(path, imageURL);
    }

    public Boolean existsByEmail(String username) {
        return hostRepository.existsByEmail(username);
    }

    public void uploadHostProfileImage(Long hostId, MultipartFile file) {
        log.info("Uploading image for host with id: " + hostId);
        Map<String, String> metadata = fileChecker.checkFile(file);
        Host host = findById(hostId);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), hostId);
        try {
            fileStore.save(path, file.getOriginalFilename(), Optional.of(metadata), file.getInputStream());
            host.setPicture(file.getOriginalFilename());
            saveHost(host);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void earnBadges(Long hostId) {
        Host host = findById(hostId);
        for (Badge badge: Badge.values()) {
            earnTravellerBadge(badge, host);
            addBookingGuruBadge(badge, host);
            earnCleanersKingBadge(badge, host);
            earnSpotlessBadge(badge, host);
        }
        saveHost(host);
    }

    public void earnTravellerBadge(Badge badge, Host host) {
        if (badge == Badge.JUNIOR_HOST && !host.getEarnedBadges().contains(Badge.JUNIOR_HOST)) {
            if (accommodationRepository.findAllByHostId(host.getId()).size() >= 3) {
                host.earnBadge(Badge.JUNIOR_HOST);
            }
        }
    }

    public void addBookingGuruBadge(Badge badge, Host host) {
        if (badge == Badge.BOOKING_GURU && !host.getEarnedBadges().contains(Badge.BOOKING_GURU)) {
            if (bookingRepository.findBookingsByHostId(host.getId()).size() > 0) {
                host.earnBadge(Badge.BOOKING_GURU);
            }
        }
    }

    public void earnCleanersKingBadge(Badge badge, Host host) {
        if (badge == Badge.CLEANERS_KING && !host.getEarnedBadges().contains(Badge.CLEANERS_KING)) {
            if (cleanerRepository.findAllByEmployerId(host.getId()).size() >= 3) {
                host.earnBadge(Badge.CLEANERS_KING);
            }
        }
    }

    public void earnSpotlessBadge(Badge badge, Host host) {
        if (badge == Badge.SPOTLESS && !host.getEarnedBadges().contains(Badge.SPOTLESS)) {
           cleanerRepository.findAll().stream().filter(cleaner -> cleaner.getEmployer() != null).filter(cleaner -> cleaner.getEmployer().getId() == (long) host.getId()).collect(Collectors.toList()).forEach(cleaner -> {
               if(cleaner.getCurrentCleaningJob() != null) {
                   host.earnBadge(Badge.SPOTLESS);
               }
           });
        }
    }

    public byte[] downloadBadgeImage(String badgeName) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), "badges");
        return fileStore.download(path, badgeName);
    }

    public List<BadgeDto> getByHost(Long hostId) {
        log.info("Fetching all badges for host with id " + hostId);
        return findById(hostId).getEarnedBadges().stream().map(badge -> badge.badgeToDto(badge.name, badge.picture, badge.description)).collect(Collectors.toList());
    }

    public void updateHost(Long id, Host host) {
        log.info("Updating details of host with id " + id);
        Host updatedHost = findById(id);
        updatedHost.setFirstName(host.getFirstName());
        updatedHost.setLastName(host.getLastName());
        updatedHost.setEmail(host.getEmail());
        updatedHost.setAddress(host.getAddress());
        updatedHost.setGender(host.getGender());
        updatedHost.setPhoneNumber(host.getPhoneNumber());
        updatedHost.setCountry(host.getCountry());
        saveHost(host);
    }
}
