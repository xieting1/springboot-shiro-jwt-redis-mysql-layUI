package com.power.authority.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.power.authority.authorization.entity.RoleEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author xieting
 * @since 2020-05-13
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {
    RoleEntity findRoleByUserId(@Param("userId") long userId);
}
