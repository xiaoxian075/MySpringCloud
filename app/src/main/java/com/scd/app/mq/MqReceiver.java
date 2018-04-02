//package com.scd.app.mq;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues="${spring.rabbitmq.queue}")
//public class MqReceiver {
//
//	@RabbitHandler
//	public void process(String msg) {
//		System.out.println("interface:" + msg);
//	}
//}
