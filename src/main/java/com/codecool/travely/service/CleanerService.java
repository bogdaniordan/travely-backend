package com.codecool.travely.service;

import com.codecool.travely.model.Cleaner;
import com.codecool.travely.repository.CleanerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CleanerService {

    private final CleanerRepository cleanerRepository;

    public List<Cleaner> findAll() {
        return cleanerRepository.findAll();
    }

    public void save(Cleaner cleaner) {
        cleanerRepository.save(cleaner);
    }
}
