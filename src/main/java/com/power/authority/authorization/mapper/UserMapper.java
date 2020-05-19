package com.power.authority.authorization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.authority.authorization.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper extends BaseMapper<UserEntity> {

    UserEntity findByAccount(@Param("account") String account);

    IPage getForPagination(Page<Object> page, @Param("keyword") String keyword, @Param("type") Integer type, @Param("roleId") Long roleId);
}
