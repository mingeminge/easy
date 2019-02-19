package com.yzm.majoryc.service;

import com.yzm.majoryc.DO.admin.UserDO;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.admin.AddUserRoleVO;

import java.util.List;

public interface UserService {

    /**
     * @param page
     * @param limit
     * @param username
     * @param phone
     * @return 用户列表
     */
    TableInfo<List<UserDO>> getUserList(Integer page, Integer limit, String username, String phone);

    /**
     * @param addUserRoleVO
     * @return 添加用户
     */
    Integer addUser(AddUserRoleVO addUserRoleVO);

    /**
     * @param userId
     * @return 通过用户id删除用户
     */
    Integer deleteUser(Integer userId);
}
