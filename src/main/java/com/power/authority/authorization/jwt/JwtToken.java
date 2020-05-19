package com.power.authority.authorization.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @program: authorization
 * @description: token令牌、主要是用户的帐号密码
 * @author: xie ting
 * @create: 2020-05-11 20:27
 */
public class JwtToken implements AuthenticationToken {

    //密钥
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
