package com.codecool.travely.data;

import com.codecool.travely.model.Customer;
import com.codecool.travely.service.AccommodationService;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataGenerator implements CommandLineRunner {

    private final CustomerService customerService;
    private final HostService hostService;
    private final AccommodationService accommodationService;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("Bogdan", "Iordan", "bogdan", "bogdan@gmail.com", "password", "Plutasilor 61", "328372983", "Male");

        customerService.saveCustomer(customer);

    }
}
