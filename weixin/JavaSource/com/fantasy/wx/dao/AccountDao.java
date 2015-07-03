package com.fantasy.wx.dao;

import com.fantasy.framework.dao.hibernate.HibernateDao;
import com.fantasy.wx.bean.Account;
import org.springframework.stereotype.Repository;

@Repository("wx.AccountDao")
public class AccountDao extends HibernateDao<Account, String> {
}
