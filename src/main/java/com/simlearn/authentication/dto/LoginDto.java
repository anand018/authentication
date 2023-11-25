package com.simlearn.authentication.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class LoginDto implements Serializable {
    private String username;
    private String password;
}
