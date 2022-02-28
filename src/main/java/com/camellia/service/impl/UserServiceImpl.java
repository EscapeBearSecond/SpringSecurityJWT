package com.camellia.service.impl;

import com.camellia.entity.Suser;
import com.camellia.mapper.UserMapper;
import com.camellia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Suser selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }
}
