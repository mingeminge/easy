package com.yzm.majoryc.service;

import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.RoleVO;
import com.yzm.majoryc.VO.admin.RolesHasRoles;

import java.util.List;

public interface RoleService {

    /**
     * @param page
     * @param limit
     * @return 角色列表
     */
    TableInfo<List<RoleVO>> getRoleList(Integer page, Integer limit);

    /**
     * @param userId
     * @return 通过用户id获取角色信息
     */
    RolesHasRoles getRoleByUserId(Integer userId);
}
