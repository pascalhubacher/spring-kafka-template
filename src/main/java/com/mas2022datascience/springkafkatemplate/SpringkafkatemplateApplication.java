package com.mas2022datascience.springkafkatemplate;

import com.github.javafaker.Faker;
import java.time.Duration;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringkafkatemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringkafkatemplateApplication.class, args);
	}

	@RequiredArgsConstructor
	@Component
	class Producer{
		private final KafkaTemplate<String, String> template;

		Faker faker;

		@EventListener(ApplicationStartedEvent.class)
		public void generate() {
			faker = Faker.instance();
			final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));

			final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

			Flux.zip(interval, quotes)
					.map(it -> template.send("hobbit", it.getT2(),
							it.getT2())).blockLast();
		}
	}

	@Component
	class Consumer{
		@KafkaListener(topics = {"hobbit"}, groupId = "spring-boot-kafka")
		public void consume(ConsumerRecord<String, String> record ) {
			System.out.println("key:value = " + record.value() + ":" + record.value());
		}
	}

}
