package org.example.authservice.dtos;


import lombok.Getter;
import lombok.Setter;
import org.example.authservice.models.User;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private String password;

    public static UserDto from(User user) {
        if(user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        return userDto;
    }
}
