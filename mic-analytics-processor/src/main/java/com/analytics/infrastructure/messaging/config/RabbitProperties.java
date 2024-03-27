package com.analytics.infrastructure.messaging.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rabbitmq")
@Data
public class RabbitProperties {

    private String queue;
    private String exchange;
    private String routingKey;
    private String host;
    private int port;
    private String username;
    private String password;
    private ListenerConfig listenerConfig;


}
