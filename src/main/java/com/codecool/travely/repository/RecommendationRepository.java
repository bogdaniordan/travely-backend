package com.codecool.travely.repository;

import com.codecool.travely.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
    List<Recommendation> findAllByReceiverId(Long id);

    List<Recommendation> findAllBySenderId(Long id);

    List<Recommendation> findAllBySenderIdAndAccommodationId(Long senderId, Long accommodationId);

}
