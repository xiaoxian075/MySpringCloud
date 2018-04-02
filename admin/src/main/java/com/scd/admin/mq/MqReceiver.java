//package com.scd.admin.mq;
//
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@RabbitListener(queues="${spring.rabbitmq.queue}")
//public class MqReceiver {
//
//	@RabbitHandler
//	public void process(String msg) {
//		System.out.println("admin:" + msg);
//	}
//}
