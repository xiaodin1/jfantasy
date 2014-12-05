package com.fantasy.wx.message.service.impl;

import com.fantasy.framework.dao.Pager;
import com.fantasy.framework.dao.hibernate.PropertyFilter;
import com.fantasy.wx.config.init.WeixinConfigInit;
import com.fantasy.wx.message.bean.Message;
import com.fantasy.wx.message.dao.MessageDao;
import com.fantasy.wx.message.service.IMessageService;
import com.fantasy.wx.user.bean.UserInfo;
import com.fantasy.wx.user.service.impl.UserInfoService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zzzhong on 2014/8/28.
 */
@Service
@Transactional
public class MessageService implements IMessageService {
    @Resource
    private MessageDao messageDao;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private WeixinConfigInit config;

    @Override
    public Pager<Message> findPager(Pager<Message> pager, List<PropertyFilter> filters) {
        Pager<Message> p = this.messageDao.findPager(pager, filters);
        for (PropertyFilter pf : filters) {
            if (pf.getFilterName().equals("EQS_userInfo.openid")) {
                for (Message m : p.getPageItems()) {
                    userInfoService.refreshMessage(m.getUserInfo());
                }
                break;
            }
        }
        return p;
    }

    @Override
    public void delete(Long... ids) {
        for (Long id : ids) {
            messageDao.delete(id);
        }
    }

    @Override
    public Message getMessage(Long id) {
        return messageDao.get(id);
    }

    @Override
    public Message save(Message message) {
        int result = 0;
        UserInfo ui = userInfoService.getUserInfo(message.getUserInfo().getOpenId());
        long createTime = new Date().getTime();
        message.setCreateTime(createTime);
        if (ui != null) {
            ui.setLastMessageTime(createTime);
            if (message.getType() != null && message.getType().equals("send")) {
                ui.setLastLookTime(createTime);
            }
        }
        this.messageDao.save(message);
        return message;
    }

    public Message createMessage(String msgType, String touser) {
        Message message = new Message();
        message.setMsgType(msgType);
        message.setType("send");
        message.setToUserName(touser);
        return message;
    }

    @Override
    public int sendTextMessage(String touser, String content) {
        Message message = createMessage(WxConsts.CUSTOM_MSG_TEXT, touser);
        message.setContent(content);
        WxMpService service = config.getUtil();
        WxMpCustomMessage customMessage = WxMpCustomMessage.TEXT().toUser(touser).content(content).build();
        try {
            service.customMessageSend(customMessage);
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 45015) {
                throw new RuntimeException("该用户48小时之内未与您发送信息");
            }
            return e.getError().getErrorCode();
        }
        save(message);
        return 0;
    }

    public int sendModelMessage(){
        return 0;
    }

}