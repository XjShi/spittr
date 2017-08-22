package com.spittr.alerts;

import com.spittr.pojo.Spittle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

public class AlertServiceImpl implements AlertService {
    private JmsOperations jmsOperations;

    @Autowired
    public AlertServiceImpl(JmsOperations jmsOperations) {
        this.jmsOperations = jmsOperations;
    }

    public void sendSpittleAlert(final Spittle spittle) {
        jmsOperations.send("spittle.alert.queue", new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(spittle);
            }
        });

        //消息发送给默认目的地
//        jmsOperations.send(new MessageCreator() {
//            public Message createMessage(Session session) throws JMSException {
//                return session.createObjectMessage(spittle);
//            }
//        });
    }
}
