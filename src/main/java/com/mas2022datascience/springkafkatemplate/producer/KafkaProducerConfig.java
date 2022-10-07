package com.mas2022datascience.springkafkatemplate.producer;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.CommonClientConfigs.RETRIES_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.BUFFER_MEMORY_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG;

import com.mas2022datascience.avro.v1.HobbitQuote;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {
  @Bean
  //public ProducerFactory<String, String> producerFactory() {
  public ProducerFactory<String, HobbitQuote> producerFactory() {
    return new DefaultKafkaProducerFactory<>(
        Map.of(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094",
        RETRIES_CONFIG, 0,
        BUFFER_MEMORY_CONFIG, 33554432,
        KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
        //VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class,
        SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081"
        ));
  }

  @Bean
  public KafkaTemplate<String, HobbitQuote> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

}
