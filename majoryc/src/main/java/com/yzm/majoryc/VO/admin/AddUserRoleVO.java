package com.yzm.majoryc.VO.admin;

import lombok.Data;

import java.util.List;

@Data
public class AddUserRoleVO {

    private UserVO user;

    private List<Integer> roleId;
}
