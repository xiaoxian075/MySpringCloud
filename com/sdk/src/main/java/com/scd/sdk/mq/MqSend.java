package com.scd.sdk.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqSend {
	private MqSend() {}
	private static class MqSendFactory {
	    private static MqSend instance = new MqSend();
	}
	public static MqSend getInstance() {
	    return MqSendFactory.instance;
	}
	
	@Autowired
    private AmqpTemplate rabbitTemplate;
	
	public void init(AmqpTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public boolean send(String queue, String data) {
		if (rabbitTemplate == null || queue == null || queue.length() == 0 || data == null || data.length() == 0) {
			return false;
		}
		
		try {
			rabbitTemplate.convertAndSend(queue, data);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
