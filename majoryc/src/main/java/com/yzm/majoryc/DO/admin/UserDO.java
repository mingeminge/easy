package com.yzm.majoryc.DO.admin;

import lombok.Data;

import java.util.Date;

@Data
public class UserDO {
    private Integer id;

    private String username;

    private String password;

    private Integer sex;

    private String phone;

    private String email;

    private String avatar;

    private Date created;

    private Date updated;
}