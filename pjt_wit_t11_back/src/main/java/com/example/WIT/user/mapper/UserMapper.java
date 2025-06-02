package com.example.WIT.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    void updateUserWithPassword(@Param("userId") int userId,
                                @Param("name") String name,
                                @Param("email") String email,
                                @Param("password") String password);

    void updateUserWithoutPassword(@Param("userId") int userId,
                                   @Param("name") String name,
                                   @Param("email") String email);


    void deleteById(@Param("userId") int userId);
}
