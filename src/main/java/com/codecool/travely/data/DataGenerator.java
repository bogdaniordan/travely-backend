package com.codecool.travely.data;

import com.codecool.travely.model.*;
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
        Accommodation accommodation = new Accommodation("Guesthouse", "Popa nan 42", "London", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, 2, PlaceType.Hotel);
        Accommodation accommodation1 = new Accommodation("Gradina monteoru", "Calea victoriei", "London", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, 22, PlaceType.Private);
        Accommodation accommodation2 = new Accommodation("Casa lu robert", "Strada golovita", "Mumbai", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, 22, PlaceType.Shared);
        Host host = new Host("Bill", "Gates", "billgates", "bill@gates.com","password");

        hostService.saveHost(host);


        accommodation.setHost(host);
        accommodation1.setHost(host);
        accommodation2.setHost(host);

//        host.setAccommodations(List.of(accommodation, accommodation1, accommodation2));



        accommodationService.saveAccommodation(accommodation);
        accommodationService.saveAccommodation(accommodation1);
        accommodationService.saveAccommodation(accommodation2);



        customerService.saveCustomer(customer);

    }
}
