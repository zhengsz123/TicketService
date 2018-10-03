package com.walmartcoding.config;


import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
@Profile({"dev", "test", "prod"})
@ComponentScan(basePackages ="com.walmartcoding")
public class JmsConfig {
    @Value("#{databaseProperties['jms.url']}")
    private String jmsUml;
    @Value("#{databaseProperties['jms.user']}")
    private String user;
    @Value("#{databaseProperties['jms.password']}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(jmsUml,user,password);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> myFactory(@Autowired ActiveMQConnectionFactory activeMQConnectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, activeMQConnectionFactory);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate(@Autowired ActiveMQConnectionFactory activeMQConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory);
        jmsTemplate.setDeliveryDelay(30000);
        return jmsTemplate;
    }

}
