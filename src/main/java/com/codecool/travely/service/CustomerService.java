package com.codecool.travely.service;

import com.codecool.travely.model.CardDetails;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.CardDetailsRepository;
import com.codecool.travely.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

   private final CustomerRepository customerRepository;
   private final CardDetailsRepository cardDetailsRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the customer with id: " + id));
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    public void saveCardDetails(CardDetails cardDetails, Long id) {
        log.info("Saving card details for customer with id: " + id);
        cardDetailsRepository.save(cardDetails);
        Customer customer = findById(id);
        customer.setCardDetails(cardDetails);
        saveCustomer(customer);
    }
}
