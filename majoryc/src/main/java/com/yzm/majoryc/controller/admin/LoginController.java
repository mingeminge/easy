package com.yzm.majoryc.controller.admin;

import com.yzm.majoryc.DTO.LoginToken;
import com.yzm.majoryc.DTO.UserToken;
import com.yzm.majoryc.VO.RestResult;
import com.yzm.majoryc.VO.admin.LoginVO;
import com.yzm.majoryc.service.LoginService;
import com.yzm.majoryc.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private RedisService redisService;

    @PostMapping("/login")
    public RestResult login(@RequestBody LoginVO loginVO) {
        LoginToken login = loginService.login(loginVO);
        return RestResult.success(login);
    }

    @GetMapping("/info")
    public RestResult info(String token) {
        UserToken info = loginService.info(token);
        return RestResult.success(info);
    }

    @PostMapping("/logout")
    public RestResult logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisService.delete(token);
        return RestResult.success();
    }

}
