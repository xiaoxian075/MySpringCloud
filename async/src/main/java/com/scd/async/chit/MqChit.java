package com.scd.async.chit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.scd.async.third.ThirdChit;
import com.scd.joggle.chit.ChitData;
import com.scd.sdk.util.GsonUtil;

@Component
@RabbitListener(queues="${spring.rabbitmq.queue.chit}")
public class MqChit {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RabbitHandler
	public void process(String data) {
		if (data == null || data.length() == 0) {
			logger.warn("queue chit receive data is null");
			return;
		}
		ChitData chitData = GsonUtil.toJson(data, ChitData.class);
		if (chitData == null) {
			logger.warn("queue chit receive data unpack fail");
			return;
		}
		
		// 进行处理
		switch (chitData.getId()) {
		case ChitData.REGISTER:
			ThirdChit.getInstance().sendRegister(chitData.getPhoneList(), chitData.getContent());
			break;
		case ChitData.GET_BACK_PASSWORD:
			ThirdChit.getInstance().sendResetPwd(chitData.getPhoneList(), chitData.getContent());
			break;
		default:
			logger.warn("queue chit not find id");
			break;
		}
		
	}
}
