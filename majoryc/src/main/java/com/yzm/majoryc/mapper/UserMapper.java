package com.yzm.majoryc.mapper;

import com.yzm.majoryc.DO.admin.UserDO;
import com.yzm.majoryc.VO.admin.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    /**
     * @param username
     * @return 登录  通过用户名获取用户信息
     */
    UserDO getUserByUserName(@Param("username") String username);

    /**
     * @param username
     * @param phone
     * @return 用户列表
     */
    List<UserDO> getUserList(@Param("username") String username, @Param("phone") String phone);

    /**
     * @param userVO
     * @return 添加用户
     */
    Integer addUser(UserVO userVO);

    /**
     * @param id
     * @return 通过用户id删除用户
     */
    Integer deleteUserById(@Param("id")Integer id);
}
