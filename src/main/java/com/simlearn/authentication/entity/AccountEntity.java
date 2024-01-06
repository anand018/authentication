package com.simlearn.authentication.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@ToString
@Document
public class AccountEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String username;
    private String[] role;
    private int failedLoginAttempts;
    private String lastLoginFailedAt;
    private List<GameEntity> gamesList;
    private byte[] profilePicture;
}