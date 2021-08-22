package com.codecool.travely.data;

import com.codecool.travely.model.Customer;
import com.codecool.travely.security.Role;
import com.codecool.travely.service.AccommodationService;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataGenerator implements CommandLineRunner {

    private final CustomerService customerService;
    private final HostService hostService;
    private final AccommodationService accommodationService;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("Bogdan", "Iordan", "bogdan", "bogdan@gmail.com", BCrypt.hashpw("password", BCrypt.gensalt(12)), "Plutasilor 61", "328372983", "Male", 222, List.of(Role.ROLE_CUSTOMER));

        customerService.saveCustomer(customer);

    }
}
