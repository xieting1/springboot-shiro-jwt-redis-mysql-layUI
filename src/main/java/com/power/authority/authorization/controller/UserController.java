package com.power.authority.authorization.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: authorization
 * @description: 用户信息相关控制器
 * @author: xie ting
 * @create: 2020-05-11 09:55
 */
@Controller
@RequestMapping("/user/")
public class UserController extends BaseController{

    /**
     * 登录页面的入口做成后台形式
     * 之后采用前后端分离的形式
     * @return
     */
    @RequestMapping(value = "toLogin", method = {RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public String login(){
        return "login";
    }


}
