package com.yzm.majoryc.VO;

import com.yzm.majoryc.enums.ResultEnums;
import lombok.Data;

@Data
public class RestResult<T> {

    private Integer code;

    private String msg;

    private T data;

    public static RestResult success(Object data) {
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(ResultEnums.SUCCESS.getCode());
        restResult.setData(data);
        restResult.setMsg("");
        return restResult;
    }

    public static RestResult success() {
        RestResult<Object> restResult = new RestResult<>();
        restResult.setCode(ResultEnums.SUCCESS.getCode());
        return restResult;
    }

    public static RestResult failed(Integer code, String msg) {
        RestResult restResult = new RestResult();
        restResult.setMsg(msg);
        restResult.setCode(code);
        return restResult;
    }

    public static RestResult failed(ResultEnums resultEnums) {
        RestResult restResult = new RestResult();
        restResult.setCode(resultEnums.getCode());
        restResult.setMsg(resultEnums.getMsg());
        return restResult;
    }
}
