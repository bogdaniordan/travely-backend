package com.codecool.travely.service;

import com.codecool.travely.dto.response.LoginResponse;
import com.codecool.travely.model.Customer;
import com.codecool.travely.repository.PasswordTokenRepository;
import com.codecool.travely.security.model.PasswordResetToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {

    private final CustomerService customerService;
    private final HostService hostService;
    private final PasswordTokenRepository passwordTokenRepository;
    private final MessageSource messageSource;

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

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, Customer customer) {
        String url = contextPath + "/auth/change-password?token=" + token;
//        String message = messageSource.getMessage("message.resetPassword",
//                null, locale);
        String message = "reset";
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

    public String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
