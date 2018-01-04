package com.spittr.service;

import com.spittr.pojo.Page;
import com.spittr.pojo.Spittle;

import java.util.List;

public interface SpittleService {
    List<Spittle> getList();
    List<Spittle> getSpittlesByPage(Page page);
    List<Spittle> getListByUsername(String username);
    List<Spittle> getListByUsernameAndPage(String username, int pageIndex, int pageSize);
    Spittle saveSpittle(Spittle spittle);
    Spittle getLastestOne(String username);
    boolean deleteSpittle(String username, long id);
    boolean queryIfExistById(long spittleId);
    boolean isSpitterHasChangePermission(String username, long id);
    Spittle getSpittleDetail(long spittleId);
}
