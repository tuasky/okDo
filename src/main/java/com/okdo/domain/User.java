package com.okdo.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@TableName(value ="user")
@Data
@Builder
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long uid;
    private String phone;
    private String type;
    private String headerUrl;
    private String status;
    private String username;
    private String password;
    private String description;
    private String sex;
    private String email;
    private Integer age;
    private Integer account;
    private String identityCard;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}