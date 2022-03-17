package com.camellia.service.impl;
import com.camellia.entity.Role;
import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.mapper.RoleMapper;
import com.camellia.mapper.UserMapper;
import com.camellia.service.UserService;
import com.camellia.util.JsonResult;
import com.camellia.util.RedisUtil;
import com.camellia.util.ResultCode;
import com.camellia.util.ResultTool;
import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Suser selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    @Override
    @Transactional
    public JsonResult register(Suser suser) {
        Suser existUser = selectUserByName(suser.getUsername());
        if (existUser == null){ // 为空，没有注册过
            // 注册
            String id = UUID.randomUUID().toString();
            suser.setId(id);
            suser.setName(id);
            suser.setPassword(passwordEncoder.encode(suser.getPassword()));
            int insertNum = userMapper.insert(suser);
            if (insertNum > 0){
                // 插入角色
                Role role_user = roleMapper.selectRoleByName("user");
                suser.setRoles(Collections.singletonList(role_user));
                suser.setEnabled(true);
                suser.setLocked(false);
                roleMapper.insertRoleByUidRid(suser.getId(),role_user.getId());
                // 构建VoUser
                VoUser voUser = new VoUser(suser);
                log.info("注册成功--{}",voUser.getUsername());
                // 返回
                return ResultTool.success(voUser);
            }else {
                log.info("用户{}已经被注册过",existUser.getUsername());
                return ResultTool.fail();
            }
        }else { // 已经注册过
            return ResultTool.fail(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
        }
    }

    @Override
    public JsonResult getUserInfo(String id) {
        Object o = redisUtil.get(id);
        JsonResult result = null;
        if (o == null){
            result = ResultTool.fail();
            log.error("获取信息失败，不存在id：{}的用户信息",id);

        }else {
            result = ResultTool.success(o);
            log.info("获取id：{}的信息成功",id);
        }
        return result;
    }
}
