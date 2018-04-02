package com.scd.async.mgr;

import org.springframework.amqp.core.AmqpTemplate;


public class Rabbitmq {

	private Rabbitmq() {
	}
	private static class RabbitmqFactory {
		private static Rabbitmq instance = new Rabbitmq();
	}
	public static Rabbitmq getInstance() {
		return RabbitmqFactory.instance;
	}
	
	private AmqpTemplate template;
    private String queueChit;
	
	public void init(AmqpTemplate template, String queueChit) {
		this.template = template;
		this.queueChit = queueChit;
	}
	
	private boolean send(String queue, String data) {
		if (queue == null || data == null || data.length() == 0) {
    		return false;
    	}
		try {
			template.convertAndSend(queue, data);
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
	
    
    
    
    public boolean sendChitMsg(String data) {
    	return send(queueChit, data);
    }
}
