package com.camellia.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_role")
@Data
public class Role {
    @TableId
    private Integer id;
    private String name;
    private String nameZh;
}
