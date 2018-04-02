package com.scd.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AsyncApplication {
	public static void main(String[] args) {
		SpringApplication.run(AsyncApplication.class, args);

//		ThirdKd.getInstance().init("http://api.kuaidi100.com/api", "6ee3eb2b4945380c");
//		Return<Kd100Vo> ret = ThirdKd.getInstance().queryOne("zhongtong", "475877026682");
//		System.out.println(ret);
	}
}
