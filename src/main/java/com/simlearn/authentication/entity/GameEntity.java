package com.simlearn.authentication.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@Document("Games")
public class GameEntity {
    private String _id;
    private String gameId;
    private String gameName;
    private String courseCode;
    private int attempts;
}
