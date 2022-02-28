package com.camellia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> selectRolesByUid(@Param("uid") String uid);
}
