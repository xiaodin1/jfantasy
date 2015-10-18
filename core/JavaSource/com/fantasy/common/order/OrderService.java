package com.fantasy.common.order;

import com.fantasy.payment.bean.Payment;

/**
 * 支付订单接口
 */
public interface OrderService {

    /**
     * 查询订单信息
     *
     * @param orderSn 编号
     * @return OrderDetails
     */
    Order loadOrderBySn(String orderSn);

    /**
     * 支付失败
     *
     * @param payment 支付信息
     */
    void payFailure(Payment payment);

    /**
     * 支付成功
     *
     * @param payment 支付信息
     */
    void paySuccess(Payment payment);

    /**
     * 获取必须的url地址配置
     *
     * @return OrderUrls
     */
    OrderUrls getOrderUrls();

}
