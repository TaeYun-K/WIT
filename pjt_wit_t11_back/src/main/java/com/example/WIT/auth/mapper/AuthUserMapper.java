package com.example.WIT.auth.mapper;

import com.example.WIT.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthUserMapper {
    User findByEmail(String email);  // 중복 확인 및 로그인용
    void insertUser(User user);            // 회원가입
    void updatePassword(int userId, String encodedPassword); //비밀번호 업데이트
}

