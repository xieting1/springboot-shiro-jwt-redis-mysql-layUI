package com.power.authority.authorization.controller;

import com.power.authority.authorization.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: authorization
 * @description: 用户信息操作相关接口
 * @author: xie ting
 * @create: 2020-05-18 10:32
 */
@RestController
@RequestMapping("/admin/")
public class AdminController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    /**
     * 权限相关方法  user:list
     * 角色限制 admin
     * @param keyword
     * @param type
     * @param roleId
     * @return
     */
    @RequiresPermissions("user:list")
    @RequiresRoles("administrator")
    @RequestMapping(value = "getForPagination", method = {RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public Object getForPagination(String keyword, Integer type, Long roleId) {
        return success(userService.getForPagination(getPage(), keyword, type, roleId));
    }
}
