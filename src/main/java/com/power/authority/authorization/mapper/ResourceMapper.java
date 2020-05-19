package com.power.authority.authorization.mapper;

import com.power.authority.authorization.entity.ResourceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源信息权限表 Mapper 接口
 * </p>
 *
 * @author xieting
 * @since 2020-05-13
 */
public interface ResourceMapper extends BaseMapper<ResourceEntity> {

    List<ResourceEntity> findByUserId(@Param("userId") long userId);

    List<Map> findResourceTreeByUserId(@Param("userId") long userId);

    List<Map> findByRoleId(@Param("rId") long rId);
}
