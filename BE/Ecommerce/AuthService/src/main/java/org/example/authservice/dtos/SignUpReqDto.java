package org.example.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpReqDto {
    private String name;
    private String email;
    private String password;
}
