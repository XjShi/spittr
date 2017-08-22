package com.spittr.mapper;

import com.spittr.pojo.Spitter;
import org.apache.ibatis.annotations.Param;

public interface SpitterMapper {
    Spitter selectSpitterById(@Param("id") String id);
    Spitter selectSpitterByUsername(@Param("username") String username);
    Integer selectSpitterCountByUsername(@Param("username") String username);
    Spitter selectSpitterByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    int getCountForAuth(@Param("username") String username, @Param("password") String password);
    void insertSpitter(Spitter spitter);
    void updateEnableStatus(Spitter spitter);
    void updateAvatar(@Param("avatar") String avatar, @Param("username") String username);
}
