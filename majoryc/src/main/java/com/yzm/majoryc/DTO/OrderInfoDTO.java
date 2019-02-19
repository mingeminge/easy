package com.yzm.majoryc.DTO;

import com.yzm.majoryc.VO.order.OrderInfoVO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderInfoDTO {
    private String orderId;//订单id

    private String buyerName;//买家名称

    private Integer status;//状态  1未完成 2完成

    private Date createTime;//创建时间

    private Date updateTime;//修改时间

    private List<OrderInfoVO> orderInfo;
}
