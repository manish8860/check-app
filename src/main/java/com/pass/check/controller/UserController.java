package com.pass.check.controller;

import com.pass.check.dto.TokenResponse;
import com.pass.check.dto.UserDto;
import com.pass.check.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create/newuser")
    @ResponseStatus(HttpStatus.CREATED)
    public void getTokenForNewUser(@RequestBody @Valid final UserDto userDto,
                                  Errors errors){
        if(errors.hasErrors()) throw new RuntimeException("Ill format of input data.");

        userService.addUser(userDto);
    }

    @PostMapping("/gettoken")
    public TokenResponse getToken(@RequestBody @Valid final UserDto userDto,
                                  Errors errors){
        if(errors.hasErrors()) throw new RuntimeException("Ill format of input data.");

        return userService.checkUser(userDto);
    }
}
