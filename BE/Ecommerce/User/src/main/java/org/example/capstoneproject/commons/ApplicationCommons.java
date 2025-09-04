package org.example.capstoneproject.commons;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApplicationCommons {

    RestTemplate restTemplate = new RestTemplate();

    public void validateToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new RuntimeException("Invalid token:token empty");
        }
        String url = "http://localhost:9000/validate/" + token;
        Boolean isValidToken = restTemplate.getForObject(url, Boolean.class);

        if (!isValidToken) {
            throw new RuntimeException("Invalid token: token is invalid");
        }
    }
}
