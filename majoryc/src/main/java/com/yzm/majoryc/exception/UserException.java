package com.yzm.majoryc.exception;

import com.yzm.majoryc.enums.ResultEnums;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public UserException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }
}
