package com.joeun.mapper;

import com.joeun.dto.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserMapper {
    int insertUser(User user);

    Optional<User> selectById(String userUseId);

    User getUserById(String id);

    void updateUser(User user);

    int getUserIntId(String userUseId);


}
