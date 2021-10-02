package com.codecool.travely.data;

import com.codecool.travely.chat.ChatMessage;
import com.codecool.travely.chat.MessageType;
import com.codecool.travely.enums.*;
import com.codecool.travely.model.*;
import com.codecool.travely.repository.ChatMessageRepository;
import com.codecool.travely.repository.RecommendationRepository;
import com.codecool.travely.service.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final RecommendationRepository recommendationRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final CommentService commentService;
    private final PostService postService;

    @Override
    public void run(String... args) {
        Customer customer = new Customer("Bogdan", "Iordan", "bogdan", "bogdan.iordan47@gmail.com", BCrypt.hashpw("password", BCrypt.gensalt(12)), "Plutasilor 61", "328372983", "Male", 222);
        customer.setPicture("di-caprio.jpg");
        Accommodation accommodation = new Accommodation("Guesthouse", "Popa nan 42", "London", 22, List.of(Facility.Fridge), PlaceType.Hotel, CleaningStatus.DIRTY);
        Accommodation accommodation1 = new Accommodation("London eye", "Calea victoriei", "London", 22, List.of(Facility.Fridge), PlaceType.Private, CleaningStatus.CLEAN);
        Accommodation accommodation2 = new Accommodation("Taj mahal", "Strada golovita", "Mumbai", 22, List.of(Facility.Fridge), PlaceType.Shared, CleaningStatus.CLEAN);
        Host host = new Host("Lil", "Baby", "billgates", "bill@gates.com", BCrypt.hashpw("password", BCrypt.gensalt(12)));
        host.setPicture("dorian-popa.jpg");
        Booking booking = new Booking(LocalDate.of(2021, 9, 9), LocalDate.of(2021,10,10), accommodation);

        Question question = new Question(LocalDate.now(), "Merge apa calda?", customer.getFirstName(), customer, host);

        hostService.saveHost(host);

        Testimonial testimonial = new Testimonial("BEST PLACE IN TOWN", accommodation, customer, 4);
        Testimonial testimonial1 = new Testimonial("THIS ACCOMMODATION IS AMAZING", accommodation, customer, 5);
        Testimonial testimonial2 = new Testimonial("DID NOT LIKE IT AT ALL", accommodation, customer, 1);


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

        Customer customer1 = new Customer("Dorian", "Popa", "dorian", "dorian.popa47gmail.com", BCrypt.hashpw("password", BCrypt.gensalt(12)), "Plutasilor 61", "328372983", "Male", 33);
        customer1.setPicture("dorian_popa.jpg");
        Customer customer2 = new Customer("Elon", "musk", "elonmusk", "elon.musk47gmail.com", BCrypt.hashpw("password", BCrypt.gensalt(12)), "Plutasilor 61", "328372983", "Male", 33);
        customer2.setPicture("elon_musk.jpg");
        customerService.saveCustomer(customer1);
        customerService.saveCustomer(customer2);

        Recommendation recommendation = new Recommendation("Check this out.", accommodation, customer1, customer);
        recommendationRepository.save(recommendation);

        ChatMessage chatMessage = new ChatMessage("Hello", customer, customer1, MessageType.SENT);
        ChatMessage chatMessage1 = new ChatMessage("Hello man", customer1, customer, MessageType.SENT);
        ChatMessage chatMessage2 = new ChatMessage("Bye", customer, customer1, MessageType.SENT);
        ChatMessage chatMessage3 = new ChatMessage("See ya", customer1, customer, MessageType.SENT);
        ChatMessage chatMessage4 = new ChatMessage("Hello man", customer2, customer, MessageType.SENT);

        chatMessageRepository.save(chatMessage);
        chatMessageRepository.save(chatMessage1);
        chatMessageRepository.save(chatMessage2);
        chatMessageRepository.save(chatMessage3);
        chatMessageRepository.save(chatMessage4);

        Booking booking1 = new Booking(LocalDate.of(2021, 8, 8), LocalDate.of(2021,9,1), accommodation1);
        Booking booking2 = new Booking(LocalDate.of(2021, 8, 8), LocalDate.of(2021,9,1), accommodation);

        bookingService.saveBooking(booking1, host.getId(), customer.getId(), accommodation1.getId());
        bookingService.saveBooking(booking2, host.getId(), customer.getId(), accommodation1.getId());

        Post post = new Post("Switzerland is the best place to spend your free time.", "Lorem ipsum dolor sit amet consectetur adipisicing elit. Quo recusandae nulla rem\n" +
                "                    eos ipsa praesentium esse magnam nemo dolor\n" +
                "                    sequi fuga quia quaerat cum, obcaecati hic, molestias minima iste voluptates.", customer, LocalDateTime.now());
        postService.save(post);
        Comment comment = new Comment("You're right", post, customer, LocalDateTime.now());
        Comment comment1 = new Comment("Damn right it is.", post, customer1, LocalDateTime.now());

        commentService.save(comment);
        commentService.save(comment1);

        Booking booking3 = new Booking(LocalDate.of(2022, 8, 8), LocalDate.of(2022,9,9), accommodation2);
        bookingService.saveBooking(booking3, host.getId(), customer.getId(), accommodation2.getId());

    }
}
