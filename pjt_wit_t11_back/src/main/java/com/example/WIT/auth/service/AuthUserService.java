package com.example.WIT.auth.service;

import com.example.WIT.auth.dto.SignupRequest;
import com.example.WIT.auth.mapper.AuthUserMapper;
import com.example.WIT.auth.util.JwtUtil;
import com.example.WIT.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserService {

    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final AuthUserMapper authUserMapper;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest request) {
        if (authUserMapper.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // 비밀번호 암호화
        user.setEmail(request.getEmail());
        user.setRoles("USER");

        authUserMapper.insertUser(user);
    }


    // 1. 이메일로 사용자 조회
    public User findByEmail(String email) {
        return authUserMapper.findByEmail(email);
    }

    // 2. 비밀번호 재설정 이메일 전송
    public void sendResetEmail(String email, int userId) {
        String token = jwtUtil.createPasswordResetToken(userId, email);
        String resetLink = "http://localhost:5173/reset-password?token=" + token;


        String subject = "[WIT] 비밀번호 재설정 안내";
        String message = "안녕하세요!\n\n비밀번호를 재설정하시려면 아래 링크를 클릭해주세요:\n\n" +
                resetLink + "\n\n해당 링크는 15분간 유효합니다.";

        emailService.send(email, subject, message);
        log.info("✅ 비밀번호 재설정 이메일 전송 완료: {}", email);
    }

    // 3. 비밀번호 변경
    public void updatePassword(int userId, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword);
        authUserMapper.updatePassword(userId, encodedPassword);
        log.info("🔐 사용자 ID {} 비밀번호 변경 완료", userId);
    }
}
