package com.yzm.majoryc.service;

import com.yzm.majoryc.DTO.LoginToken;
import com.yzm.majoryc.DTO.UserToken;
import com.yzm.majoryc.VO.admin.LoginVO;

public interface LoginService {

    /**
     * @param loginVO
     * @return 登录
     */
    LoginToken login(LoginVO loginVO);

    /**
     * @param token
     * @return 获取用户信息
     */
    UserToken info(String token);
}
