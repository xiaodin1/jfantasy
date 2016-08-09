package org.jfantasy.pay.dao.listener;

import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostUpdateEvent;
import org.jfantasy.framework.dao.hibernate.listener.AbstractChangedListener;
import org.jfantasy.pay.bean.Account;
import org.jfantasy.pay.event.AccountChangedEvent;


public class AccountChangedListener extends AbstractChangedListener<Account> {

    @Override
    public void onPostInsert(Account account, PostInsertEvent event) {
        getApplicationContext().publishEvent(new AccountChangedEvent(account));
    }

    @Override
    public void onPostUpdate(Account account, PostUpdateEvent event) {
        if (modify(event, "amount")) {
            getApplicationContext().publishEvent(new AccountChangedEvent(account));
        }
    }

}
