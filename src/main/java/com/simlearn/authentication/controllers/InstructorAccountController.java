package com.simlearn.authentication.controllers;

import com.simlearn.authentication.dto.InstructorAccountResponseDto;
import com.simlearn.authentication.entity.AccountEntity;
import com.simlearn.authentication.service.InstructorAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:5173")
public class InstructorAccountController {

    @Autowired
    private InstructorAccountService instructorAccountService;

    @GetMapping("/account/all-instructors")
    public List<InstructorAccountResponseDto> getAllInstructors() {
        return instructorAccountService.findAllInstructors();
    }
}