package com.scd.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}
	
//	//配置redis订阅功能
//    @Value("${topic.app}")
//    private String topicApp;
//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		//admin频道
//		container.addMessageListener(listenerAdapter, new PatternTopic(topicApp));
//		//inter频道
//		//container.addMessageListener(listenerAdapter, new PatternTopic(topicInterface));
//		return container;
//	}
//	@Bean
//	MessageListenerAdapter listenerAdapter() {
//		return new MessageListenerAdapter(new RedisHandler(), RedisHandler.FUNCTION_NAME);
//	}
}
