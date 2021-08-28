package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
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

@Service
@Slf4j
@AllArgsConstructor
public class HostService {

    private final HostRepository hostRepository;
    private final FileStore fileStore;
    private final FileChecker fileChecker;

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
            host.setPicture(host.getPicture());
            saveHost(host);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
