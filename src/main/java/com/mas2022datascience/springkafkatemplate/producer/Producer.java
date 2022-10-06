package com.mas2022datascience.springkafkatemplate.producer;

import com.github.javafaker.Faker;
import com.mas2022datascience.avro.v1.HobbitQuote;
import java.time.Duration;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Component
public class Producer<S, H extends SpecificRecordBase> {
  //private final KafkaTemplate<String, String> template;
  private final KafkaTemplate<String, HobbitQuote> template;

  Faker faker;

  @EventListener(ApplicationStartedEvent.class)
  public void generate() {
    faker = Faker.instance();
    final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

    final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

//    Flux.zip(interval, quotes)
//        .map(it -> template.send("main", Integer.toString(faker.random().nextInt(42)),
//            it.getT2())).blockLast();

    Flux.zip(interval, quotes)
        .map(it -> template.send("hobbit-avro",
            Integer.toString(faker.random().nextInt(42)),
            new HobbitQuote(it.getT2()))).blockLast();

  }
}
