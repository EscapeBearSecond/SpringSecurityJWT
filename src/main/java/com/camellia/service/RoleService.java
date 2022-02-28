package com.camellia.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> selectRoleByUid(String id);
}
