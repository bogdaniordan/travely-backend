package com.codecool.travely.controller;

import com.codecool.travely.dto.request.LoginRequest;
import com.codecool.travely.dto.response.LoginResponse;
import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.security.JwtTokenService;
import com.codecool.travely.security.Role;
import com.codecool.travely.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateCustomer(@Valid @RequestBody LoginRequest loginRequest) {
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

            LoginResponse loginResponse = LoginResponse.builder()
                    .id(customerService.findByUsername(username).getId())
                    .token(token)
                    .username(username)
                    .roles(roles)
                    .build();

            System.out.println(loginResponse);

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
                        customer.getGender(), customer.getAge(), List.of(Role.ROLE_CUSTOMER));

        customerService.saveCustomer(registeredCustomer);
        //send email ?
        return ResponseEntity.ok(new MessageResponse("Customer has been registered successfully!"));
    }
}
