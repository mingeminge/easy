package com.yzm.majoryc.VO.admin;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UserVO {

    private Integer id;
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotEmpty(message = "性别不能为空")
    private Integer sex;
    @NotEmpty(message = "电话号码不能为空")
    private String phone;
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    private String avatar;

    private Date created;

    private Date updated;
}
