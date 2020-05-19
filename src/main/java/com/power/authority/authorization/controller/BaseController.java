package com.power.authority.authorization.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.authority.authorization.constant.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: authorization
 * @description: 基础类--父类
 * @author: xie ting
 * @create: 2020-05-11 10:00
 */
public class BaseController {


    /**
     * 页面接口返回 -- 成功
     * @param datas
     * @return
     */
    public Object success(Object... datas) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", ResultCode.SUCCESS);
        result.put("message", ResultCode.MSG_SUCCESS);
        if (null != datas && datas.length == 1) {
            result.put("data", datas[0]);
        } else if (null != datas) {
            result.put("data", datas);
        }
        return result;
    }

//    public Long getUserId() {
//        Map<String, Object> userData = getUserData();
//        if (null != userData) {
//            return Long.parseLong(String.valueOf(userData.get(TokenConstant.USER_ID_KEY)));
//        }
//        return null;
//    }
//
//    public Map<String, Object> getUserData() {
//        HttpServletRequest request = getRequest();
//        if (null != request) {
//            return (Map<String, Object>) request.getAttribute(TokenConstant.CURRENT_USER_DATA);
//        }
//        return null;
//    }

    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (null != requestAttributes) {
            return requestAttributes.getRequest();
        } else {
            return null;
        }
    }

    public <T> Page<T> getPage() {
        Page<T> page = new Page<>();
        HttpServletRequest request = getRequest();
        if (null != request) {
            String current = request.getParameter("page");
            String size = request.getParameter("limit");
            if (StringUtils.isNotBlank(current)) {
                page.setCurrent(Long.parseLong(current));
            }
            if (StringUtils.isNotBlank(size)) {
                page.setSize(Long.parseLong(size));
            }
        }
        return page;
    }



}
