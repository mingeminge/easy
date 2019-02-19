package com.yzm.majoryc.DO.order;

import lombok.Data;

@Data
public class OrderInfoDO {

    private Integer id;

    private String orderId;

    private String treadNum;

    private String color;

    private Integer materialType;

    private Integer treadType;

    private Integer total;

    private Integer surplus;//剩余

    private String remark;
}
