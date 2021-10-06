package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.model.Car;
import com.codecool.travely.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final FileStore fileStore;

    public List<Car> findAll() {return carRepository.findAll();}

    public List<Car> findAllByLocation(String location) {
        log.info("Searching cars by location: " + location);
        return findAll().stream().filter(car -> car.getLocation().equalsIgnoreCase(location)).collect(Collectors.toList());
    }

    public void saveCar(Car car){
        carRepository.save(car);
    }

    public byte[] downloadImage(Long id) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        return fileStore.download(path, "car.jpg");
    }
}
