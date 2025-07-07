package com.project.foodCourt.infrastructure.out.webclient;

import com.project.foodCourt.infrastructure.out.webclient.response.UserRoleResponseWebClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class UserWebClient {
    
    private final WebClient webClient;
    
    @Value("${user.service.url:http://localhost:8081}")
    private String userServiceUrl;
    
    public UserWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
    
    public UserRoleResponseWebClient getUserById(Long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String token = "";
        if (auth instanceof JwtAuthenticationToken jwtToken) {
            token = "Bearer " + jwtToken.getToken().getTokenValue();
        }
        
        return webClient.get()
                .uri(userServiceUrl + "/api/v1/auth/user?userId=" + userId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserRoleResponseWebClient.class)
                .block();
    }
}