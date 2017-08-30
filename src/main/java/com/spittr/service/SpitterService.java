package com.spittr.service;

import com.spittr.pojo.Spitter;

public interface SpitterService {
    void save(Spitter spitter);
    boolean queryIfExistsByName(String username);
    Spitter getProfileById(String id, String validUsername);
    Spitter getProfileByUsername(String username, String validUsername);
    Spitter getProfileByUsername(String username);
    void updateEnabledStatus(String username, boolean enabled);
    void updateAvatar(String username, String avatar);
    boolean validateStringForUsernamePurpose(String text);
}
