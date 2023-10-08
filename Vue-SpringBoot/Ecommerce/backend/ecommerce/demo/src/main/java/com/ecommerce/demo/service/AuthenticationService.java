package com.ecommerce.demo.service;

import com.ecommerce.demo.exceptions.AuthenticationFailException;
import com.ecommerce.demo.model.AuthenticationToken;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    @Autowired
    TokenRepo tokenRepo;
    public void saveConfirmationToken(AuthenticationToken auth_token) {
        tokenRepo.save(auth_token);
    }

    public AuthenticationToken getToken(User user) {
        return  tokenRepo.findByUser(user);
    }
    public User getUser(String token){
        AuthenticationToken auth_Token = tokenRepo.findByToken(token);
        if(Objects.isNull(auth_Token)){
            return  null;
        }
       return  auth_Token.getUser();
    }
    public void authenticate(String token){
        if(Objects.isNull(token)){
            throw new AuthenticationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthenticationFailException("token not valid");
        }

    }
}
