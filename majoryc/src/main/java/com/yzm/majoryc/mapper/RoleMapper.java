package com.yzm.majoryc.mapper;

import com.yzm.majoryc.DO.admin.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {

    /**
     * @param id
     * @return 通过用户id获取用户的角色
     */
    List<RoleDO> getRoleByUserId(@Param("id") Integer id);

    /**
     * @return 角色列表
     */
    List<RoleDO>getRoleList();
}
