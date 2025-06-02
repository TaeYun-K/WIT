package com.example.WIT.auth.filter;

import com.example.WIT.auth.mapper.AuthUserMapper;
import com.example.WIT.auth.util.JwtUtil;
import com.example.WIT.domain.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthUserMapper authUserMapper;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthUserMapper authUserMapper) {
        this.jwtUtil = jwtUtil;
        this.authUserMapper = authUserMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        System.out.println("헤더 값: " + request.getHeader("Authorization"));


        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");

            try {
                String email = jwtUtil.extractEmail(token); // 👈 이메일 추출
                User user = authUserMapper.findByEmail(email);

                System.out.println("추출된 이메일 : " + email);
                System.out.println("DB 조회 결과 : " + user);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("인증객체: " + SecurityContextHolder.getContext().getAuthentication());
                }
            } catch (Exception e) {
                e.printStackTrace();
                //log.warn("JWT 인증 실패: {}", e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}
