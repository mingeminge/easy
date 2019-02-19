package com.yzm.majoryc.VO.admin;

import lombok.Data;

import java.util.List;

@Data
public class RolesHasRoles {

    private List<RoleVO> roles;

    private List<RoleVO> hasRoles;
}
