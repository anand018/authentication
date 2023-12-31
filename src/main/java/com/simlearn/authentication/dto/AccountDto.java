package com.simlearn.authentication.dto;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class AccountDto implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String[] role;
}
