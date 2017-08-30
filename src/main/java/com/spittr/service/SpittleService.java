package com.spittr.service;

import com.spittr.pojo.Spittle;

import java.util.List;

public interface SpittleService {
    List<Spittle> getList();
    List<Spittle> getListByUsername(String username);
    Spittle saveSpittle(Spittle spittle, String attachmentText);
    Spittle getLastestOne(String username);
    boolean deleteSpittle(String username, long id);
    boolean queryIfExistById(long spittleId);
    boolean isSpitterHasChangePermission(String username, long id);
    Spittle getSpittleDetail(long spittleId);
}
