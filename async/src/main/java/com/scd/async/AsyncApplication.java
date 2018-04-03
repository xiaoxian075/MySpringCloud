package com.scd.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AsyncApplication {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApplication.class, args);
	}
}
