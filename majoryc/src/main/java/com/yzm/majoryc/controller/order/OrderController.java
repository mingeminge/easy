package com.yzm.majoryc.controller.order;

import com.yzm.majoryc.DTO.DailyDTO;
import com.yzm.majoryc.DTO.OrderInfoDTO;
import com.yzm.majoryc.VO.RestResult;
import com.yzm.majoryc.VO.TableInfo;
import com.yzm.majoryc.VO.order.DailyVO;
import com.yzm.majoryc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public RestResult orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                @RequestParam(value = "time", required = false) String time,
                                @RequestParam(value = "buyerName", required = false) String buyerName,
                                @RequestParam(value = "status", required = false) Integer status) {
        TableInfo<List<OrderInfoDTO>> listTableInfo = orderService.orderList(page, limit, time, buyerName, status);
        return RestResult.success(listTableInfo);
    }

    @PostMapping("/add")
    public RestResult addOrder(@RequestBody OrderInfoDTO orderInfoDTO) {
        orderService.addOrder(orderInfoDTO);
        return RestResult.success();
    }

    @DeleteMapping("/{orderId}")
    public RestResult deleteOrder(@PathVariable("orderId") String orderId) {
        orderService.deleteOrder(orderId);
        return RestResult.success();
    }

    @PostMapping("/update")
    public RestResult updateOrder(@RequestBody OrderInfoDTO orderInfoDTO) {
        orderService.updateOrder(orderInfoDTO);
        return RestResult.success();
    }

    @PostMapping("/addDaily")
    public RestResult addDaily(@RequestBody DailyVO dailyVO) {
        orderService.addDaily(dailyVO);
        return RestResult.success();
    }

    @GetMapping("/dailyList")
    public RestResult dailyList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                @RequestParam(value = "time", required = false) String time,
                                @RequestParam(value = "workShift", required = false) Integer workShift,
                                @RequestParam(value = "treadNum", required = false) String treadNum) {
        TableInfo<List<DailyVO>> tableInfo = orderService.dailyList(page, limit, time, workShift, treadNum);
        return RestResult.success(tableInfo);
    }

    @GetMapping("/shiftOutput")
    public RestResult shiftOutput() {
        List<DailyDTO> dailyDTOS = orderService.shiftOutput();
        return RestResult.success(dailyDTOS);
    }

    @GetMapping("/outputDay")
    public RestResult outputDay() {
        List<DailyDTO> dailyDTOS = orderService.outputDay();
        return RestResult.success(dailyDTOS);
    }
}
