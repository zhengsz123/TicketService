package com.walmartcoding.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class MockJmsConfig {
    @Bean
    @Profile({"unit"})
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = Mockito.mock(JmsTemplate.class);
        jmsTemplate.setDeliveryDelay(30000);
        return jmsTemplate;
    }
}
