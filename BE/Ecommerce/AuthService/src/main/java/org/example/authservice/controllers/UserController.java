package org.example.authservice.controllers;

import org.example.authservice.dtos.*;
import org.example.authservice.models.Token;
import org.example.authservice.models.User;
import org.example.authservice.servicies.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/auth")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserDto signUp(@RequestBody SignUpReqDto signUpReqDto) {
        User user = userService.signUp(signUpReqDto.getName(), signUpReqDto.getEmail(), signUpReqDto.getPassword());
        return UserDto.from(user);
    }

    @PostMapping("/login")
    public LoginResDto signIn(@RequestBody LoginReqDto loginReqDto) {
        LoginResDto userData = userService.login(loginReqDto.getEmail(), loginReqDto.getPassword());
        return userData;
    }

    @GetMapping("/logout/{token}")
    public ResponseEntity<Boolean> logout(@PathVariable("token") String token) {
        Boolean loggedOut = userService.logout(token);
        System.out.println(loggedOut);
        if (loggedOut.equals(Boolean.TRUE)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<Boolean> validateToken(@PathVariable("token") String token){
        User user = userService.validateToken(token);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }
}
