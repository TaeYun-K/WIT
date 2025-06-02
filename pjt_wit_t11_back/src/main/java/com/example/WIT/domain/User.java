package com.example.WIT.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int userId;
    private String username;
    private String password;
    private String email;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

