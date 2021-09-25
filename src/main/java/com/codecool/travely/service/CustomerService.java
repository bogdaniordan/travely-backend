package com.codecool.travely.service;

import com.codecool.travely.aws.BucketName;
import com.codecool.travely.aws.FileStore;
import com.codecool.travely.model.CardDetails;
import com.codecool.travely.model.Customer;
import com.codecool.travely.model.FriendRequest;
import com.codecool.travely.repository.CardDetailsRepository;
import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.repository.FriendRequestRepository;
import com.codecool.travely.util.FileChecker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class CustomerService {
   // # Todo add swagger

   private final CustomerRepository customerRepository;
   private final CardDetailsRepository cardDetailsRepository;
   private final FriendRequestRepository friendRequestRepository;
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

    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public void saveCardDetails(CardDetails cardDetails, Long id) {
        log.info("Saving card details for customer with id: " + id);
        Customer customer = findById(id);
        if (customer.getCardDetails() != null) {
            CardDetails customerCardDetails = customer.getCardDetails();
            updateCardDetails(customerCardDetails, cardDetails);
        } else {
            cardDetailsRepository.save(cardDetails);
        }
        customer.setCardDetails(cardDetails);
        saveCustomer(customer);
    }

    public void updateCardDetails(CardDetails oldCardDetails, CardDetails newCardDetails) {
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
        Customer customer = findById(customerId);
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

    public boolean cardDetailsExist(Long customerId) {
        return findById(customerId).getCardDetails() != null;
    }

    public void changePassword(Customer customer, String password) {
        log.info("Updating password for customer with id: " + customer.getId());
        customer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        saveCustomer(customer);
    }

    public List<Customer> getAllCustomersExcept(long id) {
        return findAll().stream().filter(customer -> customer.getId() != id).collect(Collectors.toList());
    }

    public void addFriend(Long id, Long friendId) {
        log.info("Customer with id " + id + " is adding as friend customer with id " + friendId);
        Customer customer = findById(id);
        Customer friend = findById(friendId);
        FriendRequest friendRequest = new FriendRequest(customer, friend);
        friendRequestRepository.save(friendRequest);
    }

    public void removeFriend(Long id, Long friendId) {
        log.info("Customer with id " + id + " is removing from friends customer with id " + friendId);
        Customer customer = findById(id);
        Customer friend = findById(friendId);
        customer.removeFriend(friendId);
        friend.removeFriend(id);
        saveCustomer(customer);
        saveCustomer(friend);
    }

    public Set<Customer> getFriends(Long id) {
        log.info("Fetching friends of customer with id " + id);
        Set<Customer> friends = new HashSet<>();
        findById(id).getFriends().forEach(customerId -> friends.add(findById(customerId)));
        return friends;
    }

    public List<Customer> getSuggestedPeople(Long id) {
        log.info("Fetching suggested people for customer with id " + id);
        List<Customer> allOtherPeople = getAllCustomersExcept(id);
        allOtherPeople.removeAll(getFriends(id));
        return allOtherPeople;
    }

    public Boolean peopleAreFriends(Long firstUserId, Long secondUserId) {
        log.info("Checking if user with id " +  firstUserId + " and user with id " + secondUserId + " are friends.");
        return findById(firstUserId).getFriends().contains(secondUserId);
    }

    public Set<Customer> getMutualFriends(Long firstUserId, Long secondUserId) {
        log.info("Fetching mutual friends of user with id " + firstUserId + " and user with id " + secondUserId);
        Set<Long> firstPersonFriends = findById(firstUserId).getFriends();
        Set<Long> secondPersonFriends = findById(secondUserId).getFriends();
        firstPersonFriends.retainAll(secondPersonFriends);
        Set<Customer> mutualFriends = new HashSet<>();
        firstPersonFriends.forEach(customerId -> mutualFriends.add(findById(customerId)));
        return mutualFriends;
    }

    public Optional<FriendRequest> findFriendRequest(long receiverId, long senderId) {
        return friendRequestRepository.findAll().stream().filter(req -> req.getReceiver().getId() == receiverId && req.getSender().getId() == senderId).findFirst();
    }

    public Boolean existingPendingRequest(Long receiverId, Long senderId) {
        log.info("Checking user with id " + senderId + " has sent a friend request to user with id " + receiverId);
        Optional<FriendRequest> friendRequest = findFriendRequest(receiverId, senderId);
        return friendRequest.isPresent();
    }

    public Boolean receivedFriendRequest(Long senderId, Long receiverId) {
        log.info("Checking user with id " + receiverId + " has received a friend request from user with id " + senderId);
        Optional<FriendRequest> friendRequest = findFriendRequest(receiverId, senderId);
        return friendRequest.isPresent();
    }

    public void acceptFriendRequest(long receiverId, long senderId) {
        log.info("Customer with id " + receiverId + " is accepting the friend request from sender with id " + senderId);
        FriendRequest friendRequest = findFriendRequest(receiverId, senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find friend request."));
        friendRequestRepository.delete(friendRequest);
        Customer receiver = findById(receiverId);
        Customer sender = findById(senderId);
        receiver.addFriend(senderId);
        sender.addFriend(receiverId);
        saveCustomer(sender);
        saveCustomer(receiver);
    }


    public void denyFriendRequest(long receiverId, long senderId) {
        log.info("Customer with id " + receiverId + " is denying the friend request from sender with id " + senderId);
        FriendRequest friendRequest = findFriendRequest(receiverId, senderId)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find friend request."));
        friendRequestRepository.delete(friendRequest);
    }

    public void cancelFriendRequest(Long receiverId, Long senderId) {
        log.info("Canceling friend request from user with id " + receiverId + " to user with id " + senderId);
        Optional<FriendRequest> friendRequest = findFriendRequest(receiverId, senderId);
        friendRequest.ifPresent(friendRequestRepository::delete);
    }
}
