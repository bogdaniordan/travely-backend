package com.codecool.travely.controller;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.codecool.travely.dto.request.LoginRequest;
import com.codecool.travely.dto.request.PasswordDto;
import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
import com.codecool.travely.service.AuthService;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final HostService hostService;
    private final AuthService authService;
    private final JavaMailSender mailSender;

    @PostMapping("/sign-in/user")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            if (customerService.existsByUsername(loginRequest.getUsername())) {
                return ResponseEntity.ok(authService.getLoginResponse(loginRequest));
            }
            return ResponseEntity.badRequest().body("Username doesn't exist in the database.");
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/sign-in/host")
    public ResponseEntity<?> authenticateHost(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            if (hostService.existsByUsername(loginRequest.getUsername())) {
                return ResponseEntity.ok(authService.getLoginResponse(loginRequest));
            }
            return ResponseEntity.badRequest().body("Username doesn't exist in the database.");
        } catch (UsernameNotFoundException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register-customer")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer) {
        if (customerService.existsByUsername(customer.getUsername()) || customerService.existsByEmail(customer.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or email already exists in the database!"));
        }
        Customer registeredCustomer = new Customer(customer.getFirstName(), customer.getLastName(), customer.getUsername(), customer.getEmail(),
                BCrypt.hashpw(customer.getPassword(), BCrypt.gensalt(12)), customer.getAddress(), customer.getPhoneNumber(),
                        customer.getGender(), customer.getAge());

        customerService.saveCustomer(registeredCustomer);
        return ResponseEntity.ok(new MessageResponse("Customer has been registered successfully!"));
    }

    @PostMapping("/register-host")
    public ResponseEntity<?> registerHost(@Valid @RequestBody Host host) {
        if (hostService.existsByEmail(host.getEmail()) || hostService.existsByUsername(host.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or email already exists in the database!"));
        }
        Host registeredHost = new Host(host.getFirstName(), host.getLastName(), host.getUsername(), host.getEmail(), BCrypt.hashpw(host.getPassword(), BCrypt.gensalt(12)));
        hostService.saveHost(registeredHost);
        return ResponseEntity.ok(new MessageResponse("Host has been registered successfully!"));
    }

    @GetMapping("/reset-password/{userEmail}")
    public ResponseEntity<String> resetPassword(@PathVariable String userEmail) {
        Customer customer = customerService.findByEmail(userEmail);
        if (customer == null) {
            throw new UserNotFoundException("There is no user with email: " + userEmail);
        }
        String token = UUID.randomUUID().toString();
        authService.createPasswordResetTokenForUser(customer, token);
        mailSender.send(authService.constructResetTokenEmail(token, customer));
        return ResponseEntity.ok("Check your inbox for a reset password email.");
    }

    @GetMapping("/verify-password-token/{token}")
    public ResponseEntity<?> verifyResetPasswordToken(@PathVariable String token) {
        String result = authService.validatePasswordResetToken(token);
        if (result != null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/save-password")
    public ResponseEntity<?> savePassword(@RequestBody @Valid PasswordDto passwordDto) {
        String result = authService.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return ResponseEntity.badRequest().body("There was a problem with the password reset. Please try again!");
        }
        Optional<Customer> customer = Optional.ofNullable(authService.findByToken(passwordDto.getToken()).getUser());
        if (customer.isPresent()) {
            customerService.changePassword(customer.get(), passwordDto.getPassword());
            return ResponseEntity.ok("Password has been updated. Redirecting to login page...");
        } else {
            return ResponseEntity.badRequest().body("Could not find customer.");
        }
    }
}
