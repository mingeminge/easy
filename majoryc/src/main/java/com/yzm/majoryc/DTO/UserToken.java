package com.yzm.majoryc.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserToken {

    private String username;

    private Integer sex;

    private String phone;

    private String email;

    private String token;

    private String avatar;

    private List<String> roles;
}
