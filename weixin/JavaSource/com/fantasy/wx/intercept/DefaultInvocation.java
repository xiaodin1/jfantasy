package com.fantasy.wx.intercept;

import com.fantasy.wx.exception.WeiXinException;
import com.fantasy.wx.handler.WeiXinHandler;
import com.fantasy.wx.message.WeiXinMessage;
import com.fantasy.wx.session.WeiXinSession;

import java.util.Iterator;

/**
 * 调用者
 */
public class DefaultInvocation implements Invocation {

    public Object handler;
    private WeiXinSession session;
    private WeiXinMessage message;
    private Iterator<?> iterator;

    public DefaultInvocation(WeiXinSession session, WeiXinMessage message, Iterator<?> iterator) {
        this.session = session;
        this.message = message;
        this.handler = iterator.next();
        this.iterator = iterator;
    }

    @Override
    public WeiXinMessage invoke() throws WeiXinException {
        if (this.handler instanceof WeiXinHandler) {
            return ((WeiXinHandler) this.handler).handleMessage(this.session, message);
        } else if (this.handler instanceof WeiXinMessageInterceptor && this.iterator.hasNext()) {
            return ((WeiXinMessageInterceptor) this.handler).intercept(this.session, message, new DefaultInvocation(session, message, this.iterator));
        } else {
            throw new WeiXinException("不能处理的 handler 类型 = " + handler.getClass());
        }
    }

}
