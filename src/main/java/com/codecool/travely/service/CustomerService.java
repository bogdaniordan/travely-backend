package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.model.CardDetails;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.CardDetailsRepository;
import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.util.FileChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import static org.apache.http.entity.ContentType.*;


@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {

   private final CustomerRepository customerRepository;
   private final CardDetailsRepository cardDetailsRepository;
   private final FileStore fileStore;
   private final FileChecker fileChecker;

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
        Customer customer = findById(id);
        if (customer.getCardDetails() != null) {
            updateCardDetails(customer.getCardDetails(), cardDetails, customer);
        } else {
            cardDetailsRepository.save(cardDetails);
        }
        customer.setCardDetails(cardDetails);
        saveCustomer(customer);
    }

    public void updateCardDetails(CardDetails oldCardDetails, CardDetails newCardDetails, Customer customer) {
        oldCardDetails.setCardName(newCardDetails.getCardName());
        oldCardDetails.setCardNumber(newCardDetails.getCardNumber());
        oldCardDetails.setExpirationDate(newCardDetails.getExpirationDate());
        oldCardDetails.setCvv(newCardDetails.getCvv());
        cardDetailsRepository.save(oldCardDetails);
    }

    public void updateCustomer(Long id, Customer customer) {
        log.info("Updating customer with id: " + id);
        Customer updatedCustomer = findById(id);
        updatedCustomer.setFirstName(customer.getFirstName());
        updatedCustomer.setEmail(customer.getEmail());
        updatedCustomer.setLastName(customer.getLastName());
        updatedCustomer.setAddress(customer.getAddress());
        updatedCustomer.setAge(customer.getAge());
        updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
        updatedCustomer.setGender(customer.getGender());
        saveCustomer(updatedCustomer);
    }

    public byte[] downloadImage(Long id) {
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), id);
        String imageURL = findById(id).getPicture();
        return fileStore.download(path, imageURL);
    }

    public void uploadUserProfileImage(Long customerId, MultipartFile file) {
        log.info("Uploading image for user with id: " + customerId);
        Map<String, String> metadata = fileChecker.checkFile(file);
//        isFileEmpty(file);
//        isImage(file);
        Customer customer = findById(customerId);
//        Map<String, String> metadata = extractMetadata(file);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), customer.getId());
        String filename = file.getOriginalFilename();

        try {
            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            customer.setPicture(filename);
            customerRepository.save(customer);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }


//    public Map<String, String> extractMetadata(MultipartFile file) {
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Content-Type", file.getContentType());
//        metadata.put("Content-Length", String.valueOf(file.getSize()));
//        return metadata;
//    }
//
//    public void isImage(MultipartFile file) {
//        if (!Arrays.asList(
//                IMAGE_JPEG.getMimeType(),
//                IMAGE_PNG.getMimeType(),
//                IMAGE_GIF.getMimeType()).contains(file.getContentType())) {
//            throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
//        }
//    }
//
//    public void isFileEmpty(MultipartFile file) {
//        if (file.isEmpty()) {
//            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
//        }
//    }
}
