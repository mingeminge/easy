package com.yzm.majoryc.enums;

import lombok.Getter;

@Getter
public enum ResultEnums {
    SUCCESS(20000, "success"),
    USER_PHONE_DOES_NOT_EXIST(1, "用户手机不存在"),
    USER_PASSWORD_ERROR(2, "密码错误"),
    PARAMETER_ERROR(3, "参数错误"),
    IS_EMPTY(4, "无数据"),
    ADD_DAY_ORDER_FAILED(5, "添加失败"),
    UPDATE_ERROR(6, "修改失败"),
    USER_NOT_LOGIN(50012, "未登录"),
    USER_LOGIN_TIMEOUT(50008, "登录超时"),
    USER_LOGIN_OTHERIP(50012, "账号已在其他地方登录"),
    ERROR(500, "ERROR");

    private Integer code;

    private String msg;

    ResultEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
