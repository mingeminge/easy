package com.yzm.majoryc.config;

import com.yzm.majoryc.VO.RestResult;
import com.yzm.majoryc.enums.ResultEnums;
import com.yzm.majoryc.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionConfig {

    @ExceptionHandler(UserException.class)
    public RestResult error(UserException e) {
        log.error(e.getMessage());
        return RestResult.failed(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestResult error(Exception e) {
        log.error(e.getMessage());
        if (e instanceof DuplicateKeyException) {
            return RestResult.failed(ResultEnums.ERROR.getCode(), "该手机号已被注册");
        }
        return RestResult.failed(ResultEnums.ERROR.getCode(), e.getMessage());
    }
}
