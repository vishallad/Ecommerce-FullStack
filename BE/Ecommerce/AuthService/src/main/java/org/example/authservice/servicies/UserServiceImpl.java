package org.example.authservice.servicies;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.authservice.Repositories.TokenRepository;
import org.example.authservice.Repositories.UserRepository;
import org.example.authservice.dtos.LoginResDto;
import org.example.authservice.dtos.SendEmailDto;
import org.example.authservice.models.Token;
import org.example.authservice.models.User;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;
    public TokenRepository tokenRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    KafkaTemplate<String, String> kafkaTemplate;
    ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository, KafkaTemplate<String, String> kafkaTemplate,ObjectMapper objectMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public User signUp(String name, String email, String password){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setFrom("ghosh.abhishek18459@gmail.com");
        sendEmailDto.setTo(email);
        sendEmailDto.setBody("Hello, " + name + "! this is test email");
        sendEmailDto.setSubject("User Registration");

        String sendEmailDtoString;
        try{
           sendEmailDtoString = objectMapper.writeValueAsString(sendEmailDto);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

//        kafkaTemplate.send("sendEmail",sendEmailDtoString);

        return userRepository.save(user);
    };

    public LoginResDto login(String email, String password){
        LoginResDto resDto = new LoginResDto();
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        if(userOpt.isEmpty()){
            return null;
        }
        User user = userOpt.get();
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            return null;
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,30);
        token.setExpiryAt(calendar.getTime());
        Token tokenSaved = tokenRepository.save(token);

        resDto.setTokenValue(tokenSaved.getValue());
        resDto.setUsername(user.getName());

        return resDto;
    };

    public Boolean logout(String tokenValue){
        Optional<Token> t = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,false,new Date());
        if(!t.isPresent()){
            return Boolean.FALSE;
        }
        Token token = t.get();
        token.setDeleted(true);
        tokenRepository.save(token);
        return Boolean.TRUE;
    };

    public User validateToken(String tokenValue){
        Optional<Token> t = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(tokenValue,false,new Date());
        if(!t.isPresent()){
            return null;
        }
        return t.get().getUser();
    };
}
