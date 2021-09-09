package com.codecool.travely.service;

import com.codecool.travely.model.Recommendation;
import com.codecool.travely.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final CustomerService customerService;

    public void save(Recommendation recommendation, Long senderId, Long receiverId) {
        log.info("Saving a recommendation from sender with id: " + senderId + " to receiver with id: " + receiverId);
        recommendation.setReceiver(customerService.findById(receiverId));
        recommendation.setSender(customerService.findById(senderId));
        recommendationRepository.save(recommendation);
    }

    public List<Recommendation> findAllForReceiver(Long customerId) {
        log.info("Fetching recommendations for customer with id: " + customerId);
        return recommendationRepository.findAllByReceiverId(customerId);
    }
}
