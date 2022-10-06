package com.mas2022datascience.springkafkatemplate.admin;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class Topics {

  // creates or alters the topic
  @Bean
  NewTopic main() {
    return TopicBuilder.name("main").partitions(3).replicas(1).build();
  }

  @Bean
  NewTopic wordcount() {
    return TopicBuilder.name("streams-wordcount-output").partitions(3).replicas(1).build();
  }
}
