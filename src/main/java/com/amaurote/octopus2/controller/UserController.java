package com.amaurote.octopus2.controller;

import com.amaurote.octopus2.dto.UserRegistrationDTO;
import com.amaurote.octopus2.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String UserRegistration(UserRegistrationDTO dto) {
        userService.createNewUser(dto);
        return "redirect:/home";
    }
}
