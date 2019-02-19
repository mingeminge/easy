package com.yzm.majoryc.interceptor;

import com.yzm.majoryc.constant.TokenConstant;
import com.yzm.majoryc.enums.ResultEnums;
import com.yzm.majoryc.exception.UserException;
import com.yzm.majoryc.service.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginIntercepter implements HandlerInterceptor {
    @Autowired
    private RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod() == "OPTIONS") {
            return true;
        }
        String token = request.getHeader("token");
        if (!StringUtils.isBlank(token)) {
            if (redisService.hasKey(token)) {
                String json = redisService.get(token);
                redisService.flushToken(token, TokenConstant.expire, TimeUnit.SECONDS);
                return true;
            }
            throw new UserException(ResultEnums.USER_LOGIN_TIMEOUT);
        }
        throw new UserException(ResultEnums.USER_NOT_LOGIN);
    }
}
