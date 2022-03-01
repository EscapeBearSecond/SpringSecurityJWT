package com.camellia.service.impl;

import com.camellia.entity.Role;
import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.mapper.RoleMapper;
import com.camellia.service.RoleService;
import com.camellia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Suser suser = userService.selectUserByName(s);
        if (suser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<Role> roles = roleService.selectRoleByUid(suser.getId());
        suser.setRoles(roles);
        VoUser voUser = new VoUser(suser);
        return voUser;
    }
}
