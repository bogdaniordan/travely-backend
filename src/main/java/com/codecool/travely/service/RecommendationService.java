package com.codecool.travely.service;

import com.codecool.travely.model.user.Customer;
import com.codecool.travely.model.social.Recommendation;
import com.codecool.travely.repository.RecommendationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final CustomerService customerService;
    private final AccommodationService accommodationService;

    public void save(Recommendation recommendation, Long senderId, Long receiverId, Long accommodationId) {
        log.info("Saving a recommendation from sender with id: " + senderId + " to receiver with id: " + receiverId);
        recommendation.setAccommodation(accommodationService.findById(accommodationId));
        recommendation.setReceiver(customerService.findById(receiverId));
        recommendation.setSender(customerService.findById(senderId));
        recommendationRepository.save(recommendation);
    }

    public List<Recommendation> findAllForReceiver(Long customerId) {
        log.info("Fetching recommendations for customer with id: " + customerId);
        return recommendationRepository.findAllByReceiverId(customerId);
    }

    public List<Customer> findAllAvailableForRecommendation(long senderId, Long accommodationId) {
        log.info("Checking if there are any user where sender with id "  + senderId + "didn't recommend accommodation with id " + accommodationId);
        List<Customer> sentBySender = recommendationRepository.findAllBySenderIdAndAccommodationId(senderId, accommodationId).stream().map(Recommendation::getReceiver).collect(Collectors.toList());
        List<Customer> otherCustomers = customerService.findAll().stream().filter(customer -> customer.getId() != senderId).collect(Collectors.toList());
        otherCustomers.removeAll(sentBySender);
        return otherCustomers;
    }

    public void delete(Long id) {
        log.info("Deleting recommendation with id " + id);
        recommendationRepository.deleteById(id);
    }

    public List<Recommendation> getAllForHost(Long id) {
        log.info("Fetching all recommendations for accommodation owned by host with id " + id);
        return recommendationRepository.findAll().stream().filter(recommendation -> recommendation.getAccommodation().getHost().getId() == (long) id).collect(Collectors.toList());
    }

    public List<Recommendation> getAllForAccommodation(Long id) {
        log.info("Fetching all recommendations for accommodation with id " + id);
        return recommendationRepository.findAll().stream().filter(recommendation -> recommendation.getAccommodation().getId() == (long) id).collect(Collectors.toList());
    }
}
