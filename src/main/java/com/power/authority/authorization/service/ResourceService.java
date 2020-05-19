package com.power.authority.authorization.service;

import com.power.authority.authorization.entity.ResourceEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResourceService {

    List<ResourceEntity> findByUserId(long userId);

    List<Map> findResourceTreeByUserId(long userId);

    List<Map> findByRoleId(long rId);
}
