package com.camellia.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.entity.Suser;
import com.camellia.entity.VoUser;
import com.camellia.util.JsonResult;

public interface UserService  {
    Suser selectUserByName(String username);

    JsonResult  register(Suser suser);
}
