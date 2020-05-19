package com.power.authority.authorization.service.impl;

import com.power.authority.authorization.entity.RoleEntity;
import com.power.authority.authorization.mapper.RoleMapper;
import com.power.authority.authorization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public RoleEntity findRoleByUserId(long userId) {
        return roleMapper.findRoleByUserId(userId);
    }
}
