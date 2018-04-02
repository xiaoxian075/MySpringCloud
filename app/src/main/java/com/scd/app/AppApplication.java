package com.scd.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class AppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
//		ThirdYft.getInstance().getAccountInfo("18600000003", "a123123");
//		String password = "abc12345";
//		password = MD5Util.encodeByMD5(password);
//		AccountBo accountBo = new AccountBo("18600001004", "18600001004", password, "云粉", "", 0, 1, BigDecimal.ZERO, 0L, "", System.currentTimeMillis(), 0L);
//		ThirdYft.getInstance().register(accountBo);
	}
	
//	//配置redis订阅功能
//    @Value("${topic.admin}")
//    private String topicAdmin;
//	@Bean
//	RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//		container.setConnectionFactory(connectionFactory);
//		//admin频道
//		container.addMessageListener(listenerAdapter, new PatternTopic(topicAdmin));
//		//inter频道
//		//container.addMessageListener(listenerAdapter, new PatternTopic(topicInterface));
//		return container;
//	}
//	@Bean
//	MessageListenerAdapter listenerAdapter() {
//		return new MessageListenerAdapter(new RedisHandler(), RedisHandler.FUNCTION_NAME);
//	}	
}
