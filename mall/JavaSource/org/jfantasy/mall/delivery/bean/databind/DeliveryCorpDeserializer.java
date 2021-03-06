package org.jfantasy.mall.delivery.bean.databind;


import org.jfantasy.framework.util.common.StringUtil;
import org.jfantasy.mall.delivery.bean.DeliveryCorp;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class DeliveryCorpDeserializer extends JsonDeserializer<DeliveryCorp> {

    @Override
    public DeliveryCorp deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        Long value = jp.getValueAsLong();
        if (StringUtil.isBlank(value)) {
            return null;
        }
        return new DeliveryCorp(value);
    }

}
