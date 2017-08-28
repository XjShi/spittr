package com.spittr.service;

import com.spittr.pojo.Spittle;

import java.util.ArrayList;

public interface SpittleService {
    ArrayList<Spittle> getList();
    ArrayList<Spittle> getListByUsername(String username);
    Spittle saveSpittle(Spittle spittle);
    Spittle getLastestOne(String username);
    boolean deleteSpittle(String username, long id);
    boolean queryIfExistById(long spittleId);
    boolean isSpitterHasChangePermission(String username, long id);
}
