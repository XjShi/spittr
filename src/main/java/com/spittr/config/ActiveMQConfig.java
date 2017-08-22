package com.spittr.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class ActiveMQConfig {
    //连接工厂
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory();
    }
    //消息目的地
    @Bean
    public ActiveMQQueue spitterMQQueue() {
        return new ActiveMQQueue("spitter.queue");
    }
    //JmdTemplate
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
//        jmsTemplate.setDefaultDestinationName("spittle.alert.queue");
//        jmsTemplate.setDefaultDestination(spitterMQQueue());
        return jmsTemplate;
    }
}
