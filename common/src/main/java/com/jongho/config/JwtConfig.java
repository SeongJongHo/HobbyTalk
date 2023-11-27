package com.jongho.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-common-${spring.profiles.active:default}.properties")
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.algorithm}")
    private String algorithm;

    @Bean(name = "jwtSecretKey")
    public String getSecretKey() {
        return secretKey;
    }

    @Bean(name = "jwtAlgorithm")
    public String getAlgorithm() {
        return algorithm;
    }

}
