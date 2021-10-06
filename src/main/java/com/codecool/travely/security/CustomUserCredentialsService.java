package com.codecool.travely.security;

import com.codecool.travely.model.user.Customer;
import com.codecool.travely.model.user.Host;
import com.codecool.travely.service.CustomerService;
import com.codecool.travely.service.HostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class CustomUserCredentialsService implements UserDetailsService {

    private CustomerService customerService;
    private HostService hostService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (customerService.existsByUsername(username)) {
            Customer customer = customerService.findByUsername(username);
            return new User(customer.getUsername(), customer.getPassword(),
                    customer.getRoles().stream().map(Enum::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
        }
        Host host = hostService.findByUsername(username);
        return new User(host.getUsername(), host.getPassword(),
                host.getRoles().stream().map(Enum::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList())
        );
    }
}
