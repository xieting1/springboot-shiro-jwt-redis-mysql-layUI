package com.power.authority.authorization.service;

import com.power.authority.authorization.entity.RoleEntity;

import java.util.List;

/**
 *
 */
public interface RoleService {

    RoleEntity findRoleByUserId(long userId);

}
