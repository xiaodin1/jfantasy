
package org.jfantasy.mall.delivery.bean;

import org.jfantasy.framework.dao.BaseBusEntity;
import org.jfantasy.framework.util.jackson.JSON;
import org.jfantasy.mall.order.bean.OrderItem;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 物流项
 *
 * @author 李茂峰
 * @version 1.0
 * @since 2013-9-22 上午10:53:37
 */
@Entity
@Table(name = "MALL_DELIVERY_ITEM")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonFilter(JSON.CUSTOM_FILTER)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DeliveryItem extends BaseBusEntity {

    private static final long serialVersionUID = -6783787752984851646L;

    /**
     * 初始化物流项
     *
     * @param orderItem 订单项
     */
    public void initialize(OrderItem orderItem) {
        this.setSn(orderItem.getSn());
        this.setName(orderItem.getName());
    }

    @Id
    @Column(name = "ID", insertable = true, updatable = false)
    @GeneratedValue(generator = "fantasy-sequence")
    @GenericGenerator(name = "fantasy-sequence", strategy = "fantasy-sequence")
    private Long id;
    @Column(name = "SN", updatable = false, nullable = false)
    private String sn;// 商品货号
    @Column(name = "NAME", updatable = false, nullable = false)
    private String name;// 商品名称
    @Column(name = "QUANTITY", updatable = false, nullable = false)
    private Integer quantity;// 物流数量
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHIPPING_ID",foreignKey = @ForeignKey(name = "FK_DELIVERY_ITEM_SHIPPING"))
    private Shipping shipping;// 发货
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESHIP_ID",foreignKey = @ForeignKey(name = "FK_DELIVERY_ITEM_RESHIP"))

    private Reship reship;// 退货

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Reship getReship() {
        return reship;
    }

    public void setReship(Reship reship) {
        this.reship = reship;
    }

}