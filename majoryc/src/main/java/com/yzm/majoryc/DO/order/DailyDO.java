package com.yzm.majoryc.DO.order;

import lombok.Data;

import java.util.Date;

@Data
public class DailyDO {

    private Integer id;

    private String treadNum;

    private Date time;//时间

    private Integer output;//产量

    private Integer surplus;

    private Integer workShift;//工作班次

    private Integer infoId;//订单详情id
}
