package com.mas2022datascience.springkafkatemplate.processor;


import java.util.Arrays;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Processor {

  @Autowired
  public void processor(StreamsBuilder builder) {

    //Serializers/deserailizers
    final Serde<String> stringSerde = Serdes.String();
    final Serde<Integer> intSerde = Serdes.Integer();
    final Serde<Long> longSerde = Serdes.Long();

    KStream<String, String> textLines = builder.stream("main",
        Consumed.with(stringSerde, stringSerde));

    KTable<String, Long> wordCounts = textLines
        .flatMapValues(value -> Arrays.asList(value.toLowerCase().split("\\W+")))
        .groupBy((key, value) -> value, Grouped.with(stringSerde, stringSerde))
        .count(Materialized.as("counts"));

    wordCounts.toStream().to("streams-wordcount-output", Produced.with(stringSerde, longSerde));
  }

}
