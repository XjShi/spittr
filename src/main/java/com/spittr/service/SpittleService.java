package com.spittr.service;

import com.spittr.pojo.Spittle;

import java.util.ArrayList;

public interface SpittleService {
    ArrayList<Spittle> getList();
    ArrayList<Spittle> getListByUsername(String username);
    Spittle saveSpittle(Spittle spittle);
    Spittle getLastestOne(String username);
    void deleteSpittle(long id);
    boolean queryIfExistById(long spittleId);
}
