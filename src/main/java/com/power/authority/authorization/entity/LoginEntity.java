package com.power.authority.authorization.entity;

import java.io.Serializable;

/**
 * @program: authorization
 * @description: 封装的银行用户登录信息
 * @author: xie ting
 * @create: 2020-05-12 20:04
 */
public class LoginEntity implements Serializable {

    private static final long serialVersionUID = -5030751904183122643L;

    private long userId;
    private String account;
    private String username;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginEntity(String account) {
        this.account = account;
    }

    public LoginEntity(long userId, String account, String username) {
        this.userId = userId;
        this.account = account;
        this.username = username;
    }

}
