package com.power.authority.authorization.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.authority.authorization.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface UserService {
    UserEntity findUserByAccount(String account);

    Object login(String account, String password, String identifier, String verifyCode, HttpServletResponse response) throws Exception;

    Object loginSuccess(String account, HttpServletResponse response);

    IPage getForPagination(Page<Object> page, String keyword, Integer type, Long roleId);
}
