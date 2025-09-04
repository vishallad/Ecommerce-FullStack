package org.example.authservice.servicies;

import org.example.authservice.dtos.LoginResDto;
import org.example.authservice.models.Token;
import org.example.authservice.models.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User signUp(String name, String email, String password);
    LoginResDto login(String email, String password);
    Boolean logout(String tokenValue);
    User validateToken(String tokenValue);
}
