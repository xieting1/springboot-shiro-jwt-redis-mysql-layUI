package com.power.authority.authorization.context;

import com.power.authority.authorization.entity.LoginEntity;

/**
 * @program: authorization
 * @description: 用户信息上下文
 * @author: xie ting
 * @create: 2020-05-12 19:46
 */
public class UserContext implements AutoCloseable  {

    static final ThreadLocal<LoginEntity> current = new ThreadLocal<LoginEntity>();

    public UserContext(LoginEntity user) {
        current.set(user);
    }

    public static LoginEntity getCurrentUser() {
        return current.get();
    }

    @Override
    public void close() throws Exception {
        current.remove();
    }
}
