package com.yzm.majoryc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yzm.majoryc.DO.admin.UserDO;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.AddUserRoleVO;
import com.yzm.majoryc.VO.admin.UserRoleVo;
import com.yzm.majoryc.VO.admin.UserVO;
import com.yzm.majoryc.mapper.UserMapper;
import com.yzm.majoryc.mapper.UserRoleMapper;
import com.yzm.majoryc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public TableInfo<List<UserDO>> getUserList(Integer page, Integer limit, String username, String phone) {
        //分页
        PageHelper.startPage(page, limit);
        List<UserDO> userList = userMapper.getUserList(username, phone);
        PageInfo<UserDO> info = new PageInfo<>(userList);
        TableInfo<List<UserDO>> tableInfo = new TableInfo<>();
        tableInfo.setCount(info.getTotal());
        tableInfo.setData(userList);
        return tableInfo;
    }

    @Override
    @Transactional
    public Integer addUser(AddUserRoleVO addUserRoleVO) {
        Date date = new Date();
        //添加用户
        UserVO user = addUserRoleVO.getUser();
        user.setCreated(date);
        user.setUpdated(date);
        userMapper.addUser(user);
        //添加用户对应的角色
        List<UserRoleVo> userRoleVos = new ArrayList<>();
        addUserRoleVO.getRoleId().forEach(roleId -> {
            UserRoleVo userRoleVo = new UserRoleVo();
            userRoleVo.setRoleId(roleId);
            userRoleVo.setUserId(user.getId());
            userRoleVos.add(userRoleVo);
        });
        return userRoleMapper.addUserRole(userRoleVos);
    }

    @Override
    @Transactional
    public Integer deleteUser(Integer userId) {
        userMapper.deleteUserById(userId);
        return userRoleMapper.deleteUserRoleByUserId(userId);
    }
}
