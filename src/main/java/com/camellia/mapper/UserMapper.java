package com.camellia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.camellia.entity.Suser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper extends BaseMapper<Suser> {
    Suser selectUserByName(@Param("username") String username);
}
