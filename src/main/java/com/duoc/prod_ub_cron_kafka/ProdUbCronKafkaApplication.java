package com.duoc.prod_ub_cron_kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProdUbCronKafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProdUbCronKafkaApplication.class, args);
	}
}
