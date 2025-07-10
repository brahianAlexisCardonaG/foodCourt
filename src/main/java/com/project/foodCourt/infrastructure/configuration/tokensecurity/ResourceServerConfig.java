package com.project.foodCourt.infrastructure.configuration.tokensecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class ResourceServerConfig {

    private final JwtAuthenticationConverter jwtAuthenticationConverter;
    private String[] ListAllPermissions =
            new String[]{
                    "/api/v1/restaurant/client-restaurants",
                    "/api/v1/dish/client-dishes-restaurant",
                    "/api/v1/order/create-order-client"
            };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(ListAllPermissions)
                        .hasAnyAuthority("ADMIN", "CLIENT", "OWNER", "EMPLOYEE")
                        .requestMatchers("/api/v1/dish/**")
                        .hasAuthority("OWNER")
                        .requestMatchers("/api/v1/restaurant/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/order/**")
                        .hasAuthority("EMPLOYEE")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                );

        return http.build();
    }
}
