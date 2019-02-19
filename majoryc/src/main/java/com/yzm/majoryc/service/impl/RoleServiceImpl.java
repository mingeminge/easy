package com.yzm.majoryc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majoryc.DO.admin.RoleDO;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.RoleVO;
import com.yzm.majoryc.VO.admin.RolesHasRoles;
import com.yzm.majoryc.mapper.RoleMapper;
import com.yzm.majoryc.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public TableInfo<List<RoleVO>> getRoleList(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<RoleDO> roleList = roleMapper.getRoleList();
        List<RoleVO> roleVOList = new ArrayList<>();
        roleList.forEach(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            roleVOList.add(roleVO);
        });
        PageInfo<RoleVO> info = new PageInfo<>(roleVOList);
        TableInfo<List<RoleVO>> tableInfo = new TableInfo<>();
        tableInfo.setData(roleVOList);
        tableInfo.setCount(info.getTotal());
        return tableInfo;
    }

    @Override
    public RolesHasRoles getRoleByUserId(Integer userId) {
        List<RoleDO> roleByUserId = roleMapper.getRoleByUserId(userId);
        List<RoleVO> hasRoleList = new ArrayList<>();
        roleByUserId.forEach(role -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(role, roleVO);
            hasRoleList.add(roleVO);
        });
        List<RoleVO> roleList = new ArrayList<>();
        List<RoleDO> roleList1 = roleMapper.getRoleList();
        roleList1.forEach(item -> {
            RoleVO roleVO = new RoleVO();
            BeanUtils.copyProperties(item, roleVO);
            roleList.add(roleVO);
        });
        RolesHasRoles rolesHasRoles = new RolesHasRoles();
        rolesHasRoles.setHasRoles(hasRoleList);
        rolesHasRoles.setRoles(roleList);
        return rolesHasRoles;
    }
}
