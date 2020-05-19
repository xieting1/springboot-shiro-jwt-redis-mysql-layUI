package com.power.authority.authorization.constant;

/**
 * @program: authorization
 * @description: 接口返回信息类 -- 定义返回常量状态
 * @author: xie ting
 * @create: 2020-05-11 10:05
 */
public class ResultCode {
    public static final int FAIL = 0;
    public static final int SUCCESS = 200;
    public static final int NOT_LOGIN_REDIRECT = 202;
    public static final int NO_PERMISSION = 203;
    public static final int NOT_SUPPLY_INFO = 204;
    public static final int SYSTEM_ERROR = 299;
    //弹窗消息提示
    public static final int MSG_TIP = 600;

    public static final String MSG_SUCCESS = "操作成功";
    public static final String MSG_NOT_LOGIN_REDIRECT = "用户未登录,页面跳转...";
    public static final String MSG_NO_PERMISSION = "权限不足";
    public static final String MSG_SYSTEM_ERROR = "系统错误";
    public static final String MSG_NOT_SUPPLY_INFO = "用户资料待完善";

    public static final int NOT_ADMIN_LOGIN_REDIRECT = 101;
    public static final String MSG_ADMIN_NOT_LOGIN_REDIRECT = "用户未注册,页面跳转...";

}
