package org.example.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogOutReqDto {
    private String tokenValue;
}
