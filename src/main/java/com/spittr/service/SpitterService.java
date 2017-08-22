package com.spittr.service;

import com.spittr.pojo.Spitter;

public interface SpitterService {
    public void save(Spitter spitter);
    public boolean queryIfExistsByName(String username);
    public Spitter getProfileById(String id);
    public Spitter getProfileByUsername(String username);
    public void updateEnabledStatus(String username, boolean enabled);
    public void updateAvatar(String username, String avatar);
}
