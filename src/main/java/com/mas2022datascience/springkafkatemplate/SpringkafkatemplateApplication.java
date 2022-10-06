package com.mas2022datascience.springkafkatemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
@EnableKafkaStreams
public class SpringkafkatemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringkafkatemplateApplication.class, args);
	}

}