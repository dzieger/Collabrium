package com.dzieger.config;

import com.dzieger.repositories.UserRepository;
import com.dzieger.services.ProdUserDetailsService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public ProdUserDetailsService prodUserDetailsService(UserRepository userRepository) {
        return new ProdUserDetailsService(userRepository);
    }

}
