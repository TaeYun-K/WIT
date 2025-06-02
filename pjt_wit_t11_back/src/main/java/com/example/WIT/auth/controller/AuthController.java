package com.example.WIT.auth.controller;

import com.example.WIT.auth.dto.LoginRequest;
import com.example.WIT.auth.dto.ResetPasswordConfirmRequest;
import com.example.WIT.auth.dto.SignupRequest;
import com.example.WIT.auth.mapper.AuthUserMapper;
import com.example.WIT.auth.service.AuthUserService;
import com.example.WIT.auth.util.JwtUtil;
import com.example.WIT.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserService authUserService;
    private final AuthUserMapper authUserMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody SignupRequest request) {
        try {
            System.out.println("회원가입 시도");
            authUserService.signup(request);
            return ResponseEntity.ok("회원가입 성공!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("로그인 요청!");
        User user = authUserMapper.findByEmail(loginRequest.getEmail());

        if (user == null){
            return ResponseEntity.status(401).body("이메일 오류! 이메일을 다시 입력해주세요.");
        } else if (!(passwordEncoder.matches(loginRequest.getPassword(),user.getPassword()))) {
            return ResponseEntity.status(401).body("비밀번호 오류! 비밀번호를 다시 입력해주세요.");
        }

        String token = jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getRoles());
        return ResponseEntity.ok().body(token);
    }

    //비밀번호 찾기
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordConfirmRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();

        Claims claims = jwtUtil.parseToken(token);
        if (!"reset".equals(claims.get("type"))) {
            return ResponseEntity.badRequest().body("잘못된 토큰입니다.");
        }

        int userId = claims.get("userId", Integer.class);
        authUserService.updatePassword(userId, newPassword); // 비밀번호 암호화 포함
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

    @PostMapping("/password-reset-request")
    public ResponseEntity<?> sendResetPasswordEmail(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        User user = authUserMapper.findByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("해당 이메일로 등록된 사용자가 없습니다.");
        }

        authUserService.sendResetEmail(email, user.getUserId()); // ✅ 여기서 EmailService 내부 호출됨

        return ResponseEntity.ok("비밀번호 재설정 이메일을 전송했습니다.");
    }

    //비밀번호 폼에서 토큰 검증
    @GetMapping("/verify-reset-token")
    public ResponseEntity<?> verifyResetToken(@RequestParam String token) {
        try {
            Claims claims = jwtUtil.parseToken(token);
            if (!"reset".equals(claims.get("type"))) {
                return ResponseEntity.badRequest().body("잘못된 토큰 유형입니다.");
            }
            return ResponseEntity.ok("유효한 토큰입니다.");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(400).body("토큰이 만료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("토큰이 유효하지 않습니다.");
        }
    }


}
