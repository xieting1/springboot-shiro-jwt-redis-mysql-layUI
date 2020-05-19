package com.power.authority.authorization.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.power.authority.authorization.mapper.UserMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SysUserFilter.java
 * @author Administrator
 * @date 2016年7月1日
 * @caption
 */
public class SysUserFilter extends PathMatchingFilter {

	@Autowired
	private UserMapper userMapper;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        String account = (String)SecurityUtils.getSubject().getPrincipal();
        request.setAttribute("user", userMapper.findByAccount(account));
        return true;

    }

}