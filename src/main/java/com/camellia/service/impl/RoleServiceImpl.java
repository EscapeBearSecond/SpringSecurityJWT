package com.camellia.service.impl;

import com.camellia.entity.Role;
import com.camellia.mapper.RoleMapper;
import com.camellia.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public List<Role> selectRoleByUid(String id) {
        return roleMapper.selectRolesByUid(id);
    }
}
