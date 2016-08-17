package org.jfantasy.pay.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单接口类
 */
@ApiModel("订单详情")
public class OrderDetails {
    @ApiModelProperty("编号")
    private String sn;
    @ApiModelProperty("订单类型")
    private String type;
    @ApiModelProperty("订单摘要")
    private String subject;
    @ApiModelProperty("订单详情")
    private String Body;
    @ApiModelProperty("订单总金额")
    private BigDecimal totalFee;
    @ApiModelProperty("订单应付金额")
    private BigDecimal payableFee;
    @ApiModelProperty("订单是否可以进行支付")
    private boolean payment;
    @ApiModelProperty("订单项")
    private List<OrderItem> orderItems;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public BigDecimal getPayableFee() {
        return payableFee;
    }

    public void setPayableFee(BigDecimal payableFee) {
        this.payableFee = payableFee;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "sn='" + sn + '\'' +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", Body='" + Body + '\'' +
                ", totalFee=" + totalFee +
                ", payableFee=" + payableFee +
                ", payment=" + payment +
                ", orderItems=" + orderItems +
                '}';
    }

}