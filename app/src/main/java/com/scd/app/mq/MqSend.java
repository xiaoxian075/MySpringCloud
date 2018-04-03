package com.scd.app.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@RabbitListener(queues="${spring.rabbitmq.queue}")
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
	private String mqChit;
	
	public void init(AmqpTemplate rabbitTemplate, String mqChit) {
		this.rabbitTemplate = rabbitTemplate;
		this.mqChit = mqChit;
	}
	
	public boolean send(String data) {
		try {
			rabbitTemplate.convertAndSend(mqChit, data);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
