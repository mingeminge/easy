package com.yzm.majoryc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderIdUtil {

    public static String getOrderId() {
        Date date = new Date();
        int value = (int) Math.random() * 10;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String orderId = "YC-" + dateFormat.format(date) + value;
        return orderId;
    }

}
