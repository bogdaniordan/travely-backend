package com.codecool.travely.security.oauth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.List;


@Data
@EnableAsync
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private List<String> authorizedRedirectUris = List.of("http://localhost:3000");

    private String tokenSecret;

    private long tokenExpirationMsec;
}