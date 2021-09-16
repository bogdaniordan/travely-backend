package com.codecool.travely.controller;

import com.codecool.travely.model.CardDetails;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getById(@PathVariable Long id) {
        return new ResponseEntity<>(customerService.findById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/save-card/{id}")
    public ResponseEntity<String> saveCardDetails(@Valid @RequestBody CardDetails cardDetails, @PathVariable Long id) {
        customerService.saveCardDetails(cardDetails, id);
        return ResponseEntity.ok("Card details have been saved.");
    }

    @GetMapping("/image/{customerId}/download")
    public byte[] downloadImage(@PathVariable Long customerId) {
        return customerService.downloadImage(customerId);
    }

    @PostMapping(
            path = "/image/upload/{customerId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("customerId") Long customerId,
                                       @RequestParam("file") MultipartFile file) {
        customerService.uploadUserProfileImage(customerId, file);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateCustomer(@RequestBody Customer customer, @PathVariable Long id) {
        customerService.updateCustomer(id, customer);
        return ResponseEntity.ok("Customer has been updated");
    }

    @PreAuthorize("hasRole('CUSTOMER') or hasRole('HOST')")
    @GetMapping("/card-details-exist/{customerId}")
    public ResponseEntity<Boolean> cardDetailsExist(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.cardDetailsExist(customerId));
    }

    @GetMapping("/all-customers-except/{id}")
    public ResponseEntity<List<Customer>> getAllCustomersExcept(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getAllCustomersExcept(id));
    }
}
