package org.example.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDto {
    private String email;
    private String password;
}
