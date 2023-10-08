package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.ResponseDto;
import com.ecommerce.demo.dto.SignInDto;
import com.ecommerce.demo.dto.SignInResponseDto;
import com.ecommerce.demo.dto.SignUpDto;
import com.ecommerce.demo.exceptions.AuthenticationFailException;
import com.ecommerce.demo.exceptions.CustomException;
import com.ecommerce.demo.model.AuthenticationToken;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepo;
import jakarta.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signUp(SignUpDto signUpDto) {
        // check if user already exist
        if(Objects.nonNull(userRepo.findByEmail(signUpDto.getEmail()))){
            throw new CustomException("user already exist");
        }
        var encryptPassword =signUpDto.getPassword();
        try{
           encryptPassword = hashPassword(signUpDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            throw new CustomException(e.getMessage());
        }

        User user = new User(
                signUpDto.getFirstName(),
                signUpDto.getLastName(),
                signUpDto.getEmail(),
                encryptPassword
        );

        userRepo.save(user);

        final AuthenticationToken  auth_token = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(auth_token);
        ResponseDto responseDto = new ResponseDto("success","User created");
        return responseDto;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter.printHexBinary(digest).toUpperCase();
        return  hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepo.findByEmail(signInDto.getEmail());
        if(Objects.isNull(user)){
            throw new AuthenticationFailException("user not valid");
        }

        try {
            if (!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new AuthenticationFailException("wrong user password");
            }
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        AuthenticationToken token = authenticationService.getToken(user);
            if(Objects.isNull(token)){
                throw new CustomException("Token Does not Exist Fot This User");
            }
            return new SignInResponseDto("success", token.getToken());
    }
}
