package com.fantasy.sms.service;

import com.fantasy.framework.dao.Pager;
import com.fantasy.framework.dao.hibernate.PropertyFilter;
import com.fantasy.sms.bean.Captcha;
import com.fantasy.sms.bean.LogMobile;
import com.fantasy.sms.dao.LogMobileDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class LogMobileService {

	@Resource
	private LogMobileDao logMobileDao;

    public Pager<LogMobile> findPager(Pager<LogMobile> pager, List<PropertyFilter> filters) {
        return this.logMobileDao.findPager(pager, filters);
    }
}
