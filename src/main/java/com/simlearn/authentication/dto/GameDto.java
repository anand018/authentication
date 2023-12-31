package com.simlearn.authentication.dto;

import lombok.Data;

@Data
public class GameDto {
    private String gameId;
    private String gameName;
    private String courseCode;
    private int attempts;
}
