package com.simlearn.authentication.service;

import com.simlearn.authentication.dto.InstructorAccountResponseDto;

import java.util.List;

public interface InstructorAccountService {
    List<InstructorAccountResponseDto> findAllInstructors();
}
