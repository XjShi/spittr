package com.spittr.mapper;

import com.spittr.pojo.Spitter;
import org.apache.ibatis.annotations.Param;

public interface SpitterMapper {
    Spitter selectById(@Param("id") String id);
    Spitter selectByUsername(@Param("username") String username);
    Integer selectCountByUsername(@Param("username") String username);
    Spitter selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    int getCountForAuth(@Param("username") String username, @Param("password") String password);
    int insertOne(Spitter spitter);
    int updateEnableStatus(Spitter spitter);
    int updateAvatar(@Param("avatar") String avatar, @Param("username") String username);
    int deleteOne(@Param("username") String username);
}
