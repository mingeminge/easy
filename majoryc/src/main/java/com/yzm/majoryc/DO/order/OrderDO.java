package com.yzm.majoryc.DO.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDO {
    private String orderId;//订单id

    private String buyerName;//买家名称

    private Integer status;//状态  1未完成 2完成

    private Date createTime;//创建时间

    private Date updateTime;//修改时间
}
