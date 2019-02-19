package com.yzm.majoryc.service.impl;

import com.yzm.majoryc.DO.admin.RoleDO;
import com.yzm.majoryc.DO.admin.UserDO;
import com.yzm.majoryc.DTO.LoginToken;
import com.yzm.majoryc.DTO.UserToken;
import com.yzm.majoryc.VO.admin.LoginVO;
import com.yzm.majoryc.constant.TokenConstant;
import com.yzm.majoryc.enums.ResultEnums;
import com.yzm.majoryc.exception.UserException;
import com.yzm.majoryc.mapper.RoleMapper;
import com.yzm.majoryc.mapper.UserMapper;
import com.yzm.majoryc.service.LoginService;
import com.yzm.majoryc.service.RedisService;
import com.yzm.majoryc.utils.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisService redisService;

    @Override
    public LoginToken login(LoginVO loginVO) {
        UserDO userByUserName = userMapper.getUserByUserName(loginVO.getUsername());
        if (!userByUserName.getPassword().equals(loginVO.getPassword())) {
            throw new UserException(ResultEnums.USER_PASSWORD_ERROR);
        }
        UserToken userToken = new UserToken();
        BeanUtils.copyProperties(userByUserName, userToken);

        List<RoleDO> roleByUserId = roleMapper.getRoleByUserId(userByUserName.getId());
        List<String> roleList = new ArrayList<>();
        roleByUserId.stream().forEach(item -> {
            roleList.add(item.getRoleName());
        });
        String key = UUID.randomUUID().toString();
        userToken.setRoles(roleList);
        redisService.set(key, JsonUtils.objectToJson(userToken), TokenConstant.expire, TimeUnit.SECONDS);
        LoginToken loginToken = new LoginToken();
        loginToken.setToken(key);
        return loginToken;
    }

    @Override
    public UserToken info(String token) {
        String value = redisService.get(token);
        UserToken userToken = JsonUtils.jsonToPojo(value, UserToken.class);
        return userToken;
    }
}
