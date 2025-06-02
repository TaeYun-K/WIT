// com.example.WIT.auth.util.JwtUtil.java
package com.example.WIT.auth.util;


import com.example.WIT.auth.service.EmailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final EmailService emailService;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    public String createToken(int userId, String email, String roles) {
        return Jwts.builder()
            .setSubject(email)
            .claim("userId", userId)
            .claim("roles", roles)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
            .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
            .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //비밀번호 재설정 토큰 발급
    public String createPasswordResetToken(int userId, String email) {
        return Jwts.builder()
                .setSubject(email)
                .claim("type", "reset")
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15분 유효
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    //비밀번호 재설정 이메일 보내기
    public void sendResetEmail(String email, String token) {
        String link = "https://127.0.0.1/reset-password?token=" + token;
        String subject = "비밀번호 재설정 링크";
        String body = "아래 링크를 클릭하여 비밀번호를 재설정해주세요:\n" + link;
        emailService.send(email, subject, body);
    }

    // ✅ 토큰에서 Claims 파싱
    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("⏰ 토큰이 만료되었습니다.");
        } catch (JwtException e) {
            throw new RuntimeException("❌ 유효하지 않은 토큰입니다.");
        }
    }

}
