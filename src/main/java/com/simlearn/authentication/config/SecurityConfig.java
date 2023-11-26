package com.simlearn.authentication.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import java.beans.BeanProperty;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @BeanProperty
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeRequests().
                requestMatchers("/simlearn/*").permitAll().
                and().formLogin(withDefaults()).
                httpBasic(withDefaults());
        return http.build();
    }
}
