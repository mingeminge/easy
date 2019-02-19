package com.yzm.majoryc.VO;

import lombok.Data;

@Data
public class TableInfo<T> {

    private Long count;

    private T data;

}
