package com.yzm.majoryc.VO.order;

import lombok.Data;

import java.util.Date;

@Data
public class DailyVO {

    private String orderId;

    private Integer id;

    private Integer infoId;

    private Integer surplus;

    private Integer output;

    private Integer workShift;

    private String treadNum;

    private Date time;
}
