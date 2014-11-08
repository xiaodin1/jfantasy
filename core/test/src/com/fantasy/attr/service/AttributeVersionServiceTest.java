package com.fantasy.attr.service;

import com.fantasy.attr.bean.AttributeVersion;
import com.fantasy.framework.dao.hibernate.PropertyFilter;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class AttributeVersionServiceTest {

    private final static Log logger = LogFactory.getLog(AttributeVersionServiceTest.class);

    @Resource
    private AttributeVersionService attributeVersionService;

    @Test
    public void testSave() throws Exception {
        String className = "com.fantasy.mall.goods.bean.Goods";
        String number = "1";
        List<PropertyFilter> filters = new ArrayList<PropertyFilter>();
        filters.add(new PropertyFilter("EQS_className",className));
        filters.add(new PropertyFilter("EQS_number",number));
        List<AttributeVersion> versions = attributeVersionService.search(filters,"id","asc",1);

        Assert.assertTrue(versions.size() <= 1);

        for(AttributeVersion version : versions){
            attributeVersionService.delete(version.getId());
        }

        AttributeVersion version = new AttributeVersion();
        version.setClassName(className);
        version.setNumber(number);
        attributeVersionService.save(version);

        logger.debug("new version id = " + version.getId());

        Assert.assertNotNull(attributeVersionService.get(version.getId()));

        Assert.assertNotNull(attributeVersionService.getVersion(version.getId()));

        Assert.assertNotNull(attributeVersionService.getVersion(version.getId()));

        attributeVersionService.delete(version.getId());

    }

}