package com.ecolink.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request

                // Ecopoints kun admin
                .requestMatchers("/api/Ecopoint/**").hasRole("ADMIN")

                // Video GET alle indloggede må se videoer
                .requestMatchers(HttpMethod.GET, "/api/videos/**").authenticated()

                // Video POST, PATCH, DELETE kun content creator eller admin
                .requestMatchers(HttpMethod.POST, "/api/videos/**").hasAnyRole("CONTENT_CREATOR", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/videos/**").hasAnyRole("CONTENT_CREATOR", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/videos/**").hasAnyRole("CONTENT_CREATOR", "ADMIN")

                // Alt andet der er logget ind
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}