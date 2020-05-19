package com.power.authority.authorization.service.impl;

import com.power.authority.authorization.entity.ResourceEntity;
import com.power.authority.authorization.mapper.ResourceMapper;
import com.power.authority.authorization.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色权限操作接口实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceMapper resourceMapper;


    @Override
    public List<ResourceEntity> findByUserId(long userId) {
        return resourceMapper.findByUserId(userId);
    }

    @Override
    public List<Map> findResourceTreeByUserId(long userId) {
        return resourceMapper.findResourceTreeByUserId(userId);
    }

    @Override
    public List<Map> findByRoleId(long rId) {
        return resourceMapper.findByRoleId(rId);
    }
}
