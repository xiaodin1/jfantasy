package org.jfantasy.framework.autoconfigure;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.jfantasy.pay.service.PayProductConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@ComponentScan("org.jfantasy.pay")
@Configuration
@EntityScan("org.jfantasy.pay.bean")
public class PayAutoConfiguration {

    public final static String ONS_TAGS_TRANSACTION_KEY = "transaction";
    public final static String ONS_TAGS_PAY = "pay";
    public final static String ONS_TAGS_PAY_PAYMENTKEY = "payment";
    public static final String ONS_TAGS_PAY_REFUNDKEY = "refund";
    public static final String ONS_TAGS_ACCOUNT_KEY = "account";
    public static final String ONS_TAGS_POINT_KEY = "point";
    public static final String ONS_TAGS_CARDBIND_KEY = "card_bind";

    @Bean
    public PayProductConfiguration paymentConfiguration() {
        return new PayProductConfiguration();
    }

    @Value("${aliyun.ons.pay.accessKey}")
    private String accessKey;
    @Value("${aliyun.ons.pay.secretKey}")
    private String secretKey;

    /**
     * 发布者
     */
    @Bean(name = "pay.producer", initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean producer(@Value("${aliyun.ons.pay.producerId:PID-PAY}") String producerId) {
        ProducerBean producerBean = new ProducerBean();
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.ProducerId, producerId);
        properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
        producerBean.setProperties(properties);
        return producerBean;
    }

    /**
     * 订阅者
     *
     * @return

     @Bean(initMethod = "start", destroyMethod = "shutdown")
     public ConsumerBean consumer() {
     ConsumerBean consumerBean = new ConsumerBean();
     Properties properties = new Properties();
     properties.setProperty(PropertyKeyConst.ConsumerId, consumerId);
     properties.setProperty(PropertyKeyConst.AccessKey, accessKey);
     properties.setProperty(PropertyKeyConst.SecretKey, secretKey);
     consumerBean.setProperties(properties);
     Map<Subscription, MessageListener> subscriptionTable = new HashMap<>();
     Subscription key = new Subscription();
     key.setTopic(topicId);
     key.setExpression("pay");
     subscriptionTable.put(key, payMessageListener());
     consumerBean.setSubscriptionTable(subscriptionTable);
     return consumerBean;
     }

     @Bean public PayMessageListener payMessageListener() {
     return new PayMessageListener();
     }*/

}