package com.codecool.travely.controller;

import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import com.codecool.travely.dto.request.LoginRequest;
import com.codecool.travely.dto.response.LoginResponse;
import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
import com.codecool.travely.security.JwtTokenService;
import com.codecool.travely.util.GenericResponse;
import com.codecool.travely.service.AuthService;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final HostService hostService;
    private final AuthService authService;
    private final JavaMailSender mailSender;
    private final MessageSource messageSource;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, loginRequest.getPassword())
            );
            List<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            String token = jwtTokenService.createToken(username, roles);

            LoginResponse loginResponse = authService.getTypeOfUser(username, token, roles);
            return ResponseEntity.ok(loginResponse);

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
        //send email ?
        return ResponseEntity.ok(new MessageResponse("Customer has been registered successfully!"));
    }

    @PostMapping("/register-host")
    public ResponseEntity<?> registerHost(@Valid @RequestBody Host host) {
        if (hostService.existsByEmail(host.getEmail()) || hostService.existsByUsername(host.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username or email already exists in the database!"));
        }
        Host registeredHost = new Host(host.getFirstName(), host.getLastName(), host.getUsername(), host.getEmail(), BCrypt.hashpw(host.getPassword(), BCrypt.gensalt(12)));
        hostService.saveHost(registeredHost);
        //send email?
        return ResponseEntity.ok(new MessageResponse("Host has been registered successfully!"));
    }


    @PostMapping("/reset-password/{userEmail}")
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

}
