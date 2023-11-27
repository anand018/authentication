package com.simlearn.authentication.controllers;

import com.simlearn.authentication.dto.LoginDto;
import com.simlearn.authentication.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public boolean login(@RequestBody LoginDto logInDto) {
        return loginService.doLogin(logInDto);
    }
}