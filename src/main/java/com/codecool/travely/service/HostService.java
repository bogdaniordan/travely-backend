package com.codecool.travely.service;

import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
import com.codecool.travely.repository.HostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class HostService {

    private final HostRepository hostRepository;

    public List<Host> findAll() {
        return hostRepository.findAll();
    }

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

    public Boolean existsByEmail(String username) {
        return hostRepository.existsByEmail(username);
    }
}
