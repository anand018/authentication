package com.simlearn.authentication.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginResponseDto {
    private String username;
    private String email;
    private String firstName;
}
