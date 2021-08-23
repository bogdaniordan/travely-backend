package com.codecool.travely.repository;

import com.codecool.travely.model.ImageUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageUrlsRepository extends JpaRepository<ImageUrls, Long> {
}
