package com.codecool.travely.data;

import com.codecool.travely.enums.*;
import com.codecool.travely.model.*;
import com.codecool.travely.repository.AccommodationRepository;
import com.codecool.travely.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DataGenerator implements CommandLineRunner {

    private final CustomerService customerService;
    private final HostService hostService;
    private final AccommodationService accommodationService;
    private final BookingService bookingService;
    private final QuestionService questionService;
    private final TestimonialService testimonialService;
    private final CleanerService cleanerService;

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("Bogdan", "Iordan", "bogdan", "bogdan.iordan47@gmail.com", BCrypt.hashpw("password", BCrypt.gensalt(12)), "Plutasilor 61", "328372983", "Male", 222);
        customer.setPicture("di-caprio.jpg");
        Accommodation accommodation = new Accommodation("Guesthouse", "Popa nan 42", "London", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, PlaceType.Hotel, CleaningStatus.DIRTY);
        Accommodation accommodation1 = new Accommodation("Gradina monteoru", "Calea victoriei", "London", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, PlaceType.Private, CleaningStatus.SEMI_CLEAN);
        Accommodation accommodation2 = new Accommodation("Casa lu robert", "Strada golovita", "Mumbai", 22, List.of(Facility.Hair_dryer), AccommodationStatus.Free, PlaceType.Shared, CleaningStatus.SUPER_CLEAN);
        Host host = new Host("Lil", "Baby", "billgates", "bill@gates.com", BCrypt.hashpw("password", BCrypt.gensalt(12)));
        host.setPicture("dorian-popa.jpg");
        Booking booking = new Booking(LocalDate.of(2020, 1, 1), LocalDate.of(2021,2,2), accommodation);

        Question question = new Question(LocalDate.now(), "Merge apa calda?", customer.getFirstName(), customer, host);

        hostService.saveHost(host);

        Testimonial testimonial = new Testimonial("FRUMOS", accommodation, customer, 4);
        Testimonial testimonial1 = new Testimonial("FORZZA RAU", accommodation, customer, 5);
        Testimonial testimonial2 = new Testimonial("NU MI-A PLACUT", accommodation, customer, 1);


        accommodation.setHost(host);
        accommodation1.setHost(host);
        accommodation2.setHost(host);

        accommodationService.saveAccommodation(accommodation);
        accommodationService.saveAccommodation(accommodation1);
        accommodationService.saveAccommodation(accommodation2);



        customerService.saveCustomer(customer);

        bookingService.saveBooking(booking, host.getId(), customer.getId(), accommodation.getId());

        questionService.save(question);

        testimonialService.save(testimonial);
        testimonialService.save(testimonial1);

        testimonialService.save(testimonial2);

        Cleaner cleaner = new Cleaner("Bill Gates", CleaningExperience.BEGINNER);
        Cleaner cleaner1 = new Cleaner("Elon Musk", CleaningExperience.INTERMEDIATE);
        Cleaner cleaner2 = new Cleaner("David Beckham", CleaningExperience.SENIOR);
        Cleaner cleaner3 = new Cleaner("Lewis Hamilton", CleaningExperience.SENIOR);

        cleanerService.save(cleaner);
        cleanerService.save(cleaner1);
        cleanerService.save(cleaner2);
        cleanerService.save(cleaner3);

    }
}
