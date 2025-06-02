package com.example.WIT.user.service;

import com.example.WIT.user.dto.UpdateUserRequest;
import com.example.WIT.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void updateUser(int userId, UpdateUserRequest request) {
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            String encodedPassword = passwordEncoder.encode(request.getPassword());
            userMapper.updateUserWithPassword(userId, request.getUsername(), request.getEmail(), encodedPassword);
        } else {
            userMapper.updateUserWithoutPassword(userId, request.getUsername(), request.getEmail());
        }
    }

    public void deleteUserById(int userId) {
        userMapper.deleteById(userId);
    }
}
