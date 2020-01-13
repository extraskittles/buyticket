package com.skittles.buyticket.service;

import com.skittles.buyticket.param.ConfirmOrderParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface OrderService {
    Map<String,Boolean> pay(int orderId, HttpServletRequest request);
    Map<String,Object> confirmOrder(ConfirmOrderParam confirmOrderParam, int id);
}
