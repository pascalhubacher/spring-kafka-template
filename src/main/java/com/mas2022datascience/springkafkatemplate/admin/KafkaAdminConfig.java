package com.mas2022datascience.springkafkatemplate.admin;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaAdminConfig {

    @Bean
    public KafkaAdmin admin() {
        return new KafkaAdmin((Map.of(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")));
    }

}
