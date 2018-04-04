package com.scd.async.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.scd.joggle.mq.PushData;
import com.scd.sdk.util.GsonUtil;
import com.third.jgsdk.JGPush;

import cn.jpush.api.push.PushResult;

@Component
@RabbitListener(queues="${spring.rabbitmq.queue.push}")
public class MqPush {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RabbitHandler
	public void process(String data) {
		if (data == null || data.length() == 0) {
			logger.warn("queue push receive data is null");
			return;
		}
		PushData pushData = GsonUtil.toJson(data, PushData.class);
		if (pushData == null) {
			logger.warn("queue push receive data unpack fail");
			return;
		}
		
		PushResult pushResult = JGPush.getInstance().pushAll(pushData.getId(), pushData.getContent());
		if (pushResult == null) {
			logger.warn("queue push fail");
		}
	}
}
