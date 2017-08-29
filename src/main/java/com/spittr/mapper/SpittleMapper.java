package com.spittr.mapper;

import com.spittr.pojo.Spittle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SpittleMapper {
    List<Spittle> selectAll();
    List<Spittle> selectByUsername(String username);
    void insertSpittle(Spittle spittle);
    Spittle getLatestOne(@Param("username") String username);
    void deleteSpittleById(@Param("id") long id);
    int selectSpittleCountById(@Param("id") long id);
    Spittle getSpittleById(@Param("id") long id);
    Spittle selectOne(@Param("id") long id);
}
