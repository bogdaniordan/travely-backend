package com.codecool.travely.security.oauth;

import com.codecool.travely.enums.AuthProvider;
import com.codecool.travely.exception.customs.OAuth2AuthenticationProcessingException;
import com.codecool.travely.model.user.Customer;
import com.codecool.travely.repository.CustomerRepository;
import com.codecool.travely.security.UserPrincipal;
import com.codecool.travely.security.oauth.user.OAuth2UserInfo;
import com.codecool.travely.security.oauth.user.OAuth2UserInfoFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerRepository userRepository;

    @Autowired
    public CustomOAuth2UserService(CustomerRepository customerRepository) {
        this.userRepository = customerRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
        }
        Optional<Customer> userOptional = userRepository.findCustomerByEmail(oAuth2UserInfo.getEmail());
        Customer user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private Customer registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
        Customer user = new Customer();
        // #Todo remove
        System.out.println(oAuth2UserInfo.getImageUrl());
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));;
        setUserName(oAuth2UserInfo, user);
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setPicture(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private Customer updateExistingUser(Customer existingUser, OAuth2UserInfo oAuth2UserInfo) {
        setUserName(oAuth2UserInfo, existingUser);
        existingUser.setPicture(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

    public void setUserName(OAuth2UserInfo oAuth2UserInfo, Customer user) {
        if (oAuth2UserInfo.getName().contains(" ")) {
            user.setFirstName(oAuth2UserInfo.getName().substring(0, oAuth2UserInfo.getName().indexOf(" ")));
            user.setLastName(oAuth2UserInfo.getName().substring(oAuth2UserInfo.getName().indexOf(" ") + 1));
        } else {
            user.setFirstName(oAuth2UserInfo.getName());
        }
    }
}
