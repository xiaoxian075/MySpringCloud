package com.scd.async.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scd.async.mgr.Rabbitmq;
import com.scd.async.third.ThirdChit;
import com.scd.async.third.ThirdKd;
import com.third.jgsdk.JGPush;


@Component
@Order(value=1)
public class CommonConfig implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	@Value("${spring.rabbitmq.queue.chit}")
    private String queueChit;
	
	@Value("${third.express.kd100.url}")
    private String kd100Url;
	@Value("${third.express.kd100.key}")
    private String kd100Key;
	
	@Value("${third.sms.cl.url}")
    private String clUrl;
	@Value("${third.sms.cl.account}")
    private String clAccount;
	@Value("${third.sms.cl.password}")
    private String clPassword;
	
	@Value("${third.push.jg.mastersecret}")
    private String masterSecret;
	@Value("${third.push.jg.appkey}")
    private String appKey;
	@Value("${third.push.jg.isProduct}")
    private int isProduct;

	@Override
	public void run(String... arg0) throws Exception {
		
		// 初始化rabbitmq
		if (rabbitTemplate == null || queueChit == null) {
			logger.error("init rabbitmq error");
			return;
		}
		Rabbitmq.getInstance().init(rabbitTemplate, queueChit);
		
		// 初始化快递100
		if (kd100Url == null || kd100Key == null) {
			logger.error("init kuai di 100 error");
			return;
		}
		ThirdKd.getInstance().init(kd100Url, kd100Key);
		
		// 初始化创蓝短信
		if (clUrl == null || clAccount == null || clPassword == null) {
			logger.error("init change lang error");
			return;
		}
		ThirdChit.getInstance().init(clUrl, clAccount, clPassword);
		
		// 初始化极光
		if (appKey == null || masterSecret == null) {
			logger.error("init ji gong error");
			return;
		}
		boolean bProduct = false;
		if (isProduct == 1) {
			bProduct = true;
		}
		JGPush.getInstance().init(masterSecret, appKey, bProduct);
	}

}
