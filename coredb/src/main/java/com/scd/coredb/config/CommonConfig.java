package com.scd.coredb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scd.coredb.third.ThirdYft;


@Component
@Order(value=1)
public class CommonConfig implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${yft.sing.key}")
	private String yftSingKey;
	@Value("${yft.yzj.client.id}")
    private String yftYzjClientId;
    @Value("${yft.yzj.receive.account}")
    private String yftYzjReceiveAccount;
    @Value("${yft.yzj.pay.timeout}")
    private long yftYzjPayTimeout;
    
	@Override
	public void run(String... args) throws Exception {
		// 初始化云付通
		if (yftSingKey == null || yftYzjReceiveAccount == null || yftYzjClientId == null || yftYzjPayTimeout <= 0) {
			logger.error("init yft error");
			return;
		}
		ThirdYft.getInstance().init(yftSingKey, yftYzjClientId, yftYzjReceiveAccount, yftYzjPayTimeout);
		//yftYzjBo = new YftYzjBo(yftYzjClientId, yftYzjReceiveAccount, yftYzjPayTimeout*1000);
	}
	
	
//	private static YftYzjBo yftYzjBo;
//	public static YftYzjBo getYzjInfo() {
//		return yftYzjBo;
//	}
}
