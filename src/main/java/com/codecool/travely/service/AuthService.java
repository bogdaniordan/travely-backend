package com.codecool.travely.service;

import com.codecool.travely.dto.response.LoginResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.PasswordTokenRepository;
import com.codecool.travely.security.PasswordResetToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final HostService hostService;
    private final PasswordTokenRepository passwordTokenRepository;

    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token);
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

    public void createPasswordResetTokenForUser(Customer customer, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, customer);
        passwordTokenRepository.save(myToken);
    }

    public SimpleMailMessage constructResetTokenEmail(String token, Customer customer) {
        String url = "http://localhost:3000/reset-password/" + token;
        String message = "Please click the link below to reset your password.";
        return constructEmail("Reset Password", message + " \r\n" + url, customer);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             Customer customer) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(customer.getEmail());
        email.setFrom("noreply@travely");
        return email;
    }


    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
