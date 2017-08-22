package com.spittr.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Spitter implements Serializable {
    private long id;
    @NotNull
    @Size(min = 3, max = 16)
    private String username;
    @NotNull
    @Size(min = 3, max = 16)
    private String password;
    private boolean enabled;
    private String avatar;
    private Timestamp registrationDate;

    public Spitter() {

    }

    public Spitter(String username, String password, boolean enabled, Timestamp registrationDate) {
        this(-1, username, password, enabled, registrationDate);
    }

    public Spitter(long id, String username, String password, boolean enabled, Timestamp registrationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.registrationDate = registrationDate;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "Spitter{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
