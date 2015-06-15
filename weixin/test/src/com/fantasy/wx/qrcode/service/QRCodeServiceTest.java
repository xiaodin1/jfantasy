package com.fantasy.wx.qrcode.service;

import com.fantasy.framework.dao.Pager;
import com.fantasy.framework.dao.hibernate.PropertyFilter;
import com.fantasy.framework.util.jackson.JSON;
import com.fantasy.wx.bean.QRCode;
import com.fantasy.wx.service.QRCodeWeiXinService;
import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext.xml"})
public class QRCodeServiceTest {
    private static final Log logger = LogFactory.getLog(QRCode.class);
    @Autowired
    private QRCodeWeiXinService iQRCodeService;

    @Test
    public void testGetQRCode() throws Exception {
        Pager<QRCode> pager = iQRCodeService.findPager(new Pager<QRCode>(), new ArrayList<PropertyFilter>());
        QRCode code = iQRCodeService.getQRCode(pager.getPageItems().get(0).getId());
        Assert.assertNotNull(code);
        logger.debug(JSON.serialize(code));
    }

    @Test
    public void testQrCodeCreateTmpTicket() throws Exception {
        QRCode code = iQRCodeService.qrCodeCreateTmpTicket("测试1", null);
        Assert.assertNotNull(code.getId());
    }

    @Test
    public void testQrCodeCreateLastTicket() throws Exception {
        QRCode code = iQRCodeService.qrCodeCreateLastTicket("测试2");
        Assert.assertNotNull(code.getId());
    }
}