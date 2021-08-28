package com.codecool.travely.controller;

import com.codecool.travely.dto.request.LoginRequest;
import com.codecool.travely.dto.response.LoginResponse;
import com.codecool.travely.dto.response.MessageResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.model.Host;
import com.codecool.travely.security.JwtTokenService;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
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
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthController {

    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final HostService hostService;

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

            LoginResponse loginResponse = getTypeOfUser(username, token, roles);
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

    public LoginResponse getTypeOfUser(String username, String token, List<String> roles) {
        LoginResponse loginResponse;
        if (customerService.existsByUsername(username)) {
            loginResponse = LoginResponse.builder()
                    .id(customerService.findByUsername(username).getId())
                    .token(token)
                    .username(username)
                    .roles(roles)
                    .build();
        } else {
            loginResponse = LoginResponse.builder()
                    .id(hostService.findByUsername(username).getId())
                    .token(token)
                    .username(username)
                    .roles(roles)
                    .build();
        }
        return loginResponse;
    }
}
