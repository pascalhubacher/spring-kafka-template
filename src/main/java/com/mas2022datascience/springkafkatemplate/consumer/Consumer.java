package com.mas2022datascience.springkafkatemplate.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
  @KafkaListener(topics = {"main"}, groupId = "spring-boot-kafka")
  public void consume(ConsumerRecord<String, String> record ) {
    System.out.println("key:value = " + record.value() + ":" + record.value());
  }
}

