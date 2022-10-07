package com.mas2022datascience.springkafkatemplate.consumer;

import com.mas2022datascience.avro.v1.HobbitQuote;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

  @KafkaListener(topics = {"hobbit-avro"}, groupId = "spring-boot-kafka")
  public void consume(ConsumerRecord<String, HobbitQuote> record ) {
    System.out.println("key:value = " + record.key() + ":" + record.value());
  }

}

