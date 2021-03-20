package com.cyquen.presentcloud.security.jwt;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class AccessToken implements Serializable {

    private String token;

    private String tokenType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expiresIn;

    public AccessToken(String token, Date expiresIn) {
        this.token = token;
        this.tokenType = "bearer";
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return tokenType;
    }

    public void setType(String tokenType) {
        this.token = tokenType;
    }

    public Date getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Date expiresIn) {
        this.expiresIn = expiresIn;
    }

}
