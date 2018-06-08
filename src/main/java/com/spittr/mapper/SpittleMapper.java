package com.spittr.mapper;

import com.spittr.pojo.Spittle;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SpittleMapper {
    List<Spittle> selectByUsernameAndPage(@Param("username") String username, @Param("start") int start, @Param("limit") int limit);
    int insertSpittle(Spittle spittle);
    Spittle getLatestOne(@Param("username") String username);
    int deleteSpittleById(@Param("id") long id);
    int selectSpittleCountById(@Param("id") long id);
    Spittle selectOne(@Param("id") long id);
}
