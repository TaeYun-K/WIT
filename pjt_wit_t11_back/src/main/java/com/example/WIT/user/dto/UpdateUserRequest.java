package com.example.WIT.user.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String password; // 변경하지 않으면 null
}
