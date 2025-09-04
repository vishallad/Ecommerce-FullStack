package org.example.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResDto {
    private String tokenValue;
    private String username;
}
