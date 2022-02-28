package com.camellia.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.entity.Suser;

public interface UserService  {
    Suser selectUserByName(String username);
}
