package com.camellia.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("tb_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Suser implements Serializable {
    @TableId
    private String id;
    private String username;
    private String name;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    @TableField(fill = FieldFill.INSERT)
    private Date gmt_create;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmt_modified;
    List<Role> roles;
}
