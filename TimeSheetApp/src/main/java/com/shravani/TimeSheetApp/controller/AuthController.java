package com.shravani.TimeSheetApp.controller;

import com.shravani.TimeSheetApp.dto.UserDto;
import com.shravani.TimeSheetApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>>  getUsers()
    {
        List<UserDto> users=userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/register")

    public ResponseEntity<UserDto> registeredUser(@Valid @RequestBody UserDto userDto,BindingResult result)
    {
        UserDto existingUser=userService.findUserByEmail(userDto.getEmail());
        if(existingUser !=null && existingUser.getEmail() !=null && !existingUser.getEmail().isEmpty())
        {
            result.rejectValue("email",null,"There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(userDto);
        }
        UserDto savedUser=userService.saveUser(userDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

 /*   @GetMapping("/user")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication)
    {
        String userName=authentication.getName();
        UserDto existingUser=userService.findUserByName(userName);

        if(existingUser!=null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(existingUser);
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }*/


}
