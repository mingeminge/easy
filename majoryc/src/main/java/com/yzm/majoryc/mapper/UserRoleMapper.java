package com.yzm.majoryc.mapper;

import com.yzm.majoryc.VO.admin.UserRoleVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper {

    /**
     * @param userRoleVos
     * @return 添加用户对应的角色关系
     */
    Integer addUserRole(@Param("userRoleVos") List<UserRoleVo> userRoleVos);

    /**
     * @param userId
     * @return 通过用户id删除用户对应的角色关系
     */
    Integer deleteUserRoleByUserId(@Param("userId") Integer userId);
}
