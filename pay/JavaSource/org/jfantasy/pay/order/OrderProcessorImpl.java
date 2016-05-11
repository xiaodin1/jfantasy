package org.jfantasy.pay.order;


import org.jfantasy.framework.util.common.BeanUtil;
import org.jfantasy.pay.order.entity.OrderKey;
import org.jfantasy.pay.order.entity.RefundDetails;
import org.jfantasy.pay.service.RefundService;
import org.jfantasy.rpc.annotation.ServiceExporter;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@ServiceExporter(targetInterface = OrderProcessor.class)
public class OrderProcessorImpl implements OrderProcessor {

    @Autowired
    private RefundService refundService;

    @Override
    public RefundDetails refund(OrderKey key, BigDecimal amount, String remark) {
        return BeanUtil.copyProperties(new RefundDetails(), refundService.ready(key, amount, remark));
    }

}
