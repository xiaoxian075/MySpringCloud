package com.scd.joggle.mq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import com.scd.sdk.mq.MqSend;
import com.scd.sdk.util.GsonUtil;

@Component
public class MyMqSend {
	private MyMqSend() {}
	private static class MyMqSendFactory {
	    private static MyMqSend instance = new MyMqSend();
	}
	public static MyMqSend getInstance() {
	    return MyMqSendFactory.instance;
	}
	
	private String mqChit;
	private String mqPush;
	
	public void init(AmqpTemplate rabbitTemplate, String mqChit, String mqPush) {
		MqSend.getInstance().init(rabbitTemplate);
		this.mqChit = mqChit;
		this.mqPush = mqPush;
	}
	
	public boolean sendPush(long id, String content) {
		if (mqPush == null || mqPush.length() == 0) {
			return false;
		}
		
		try {
			PushData pushData = new PushData(id, content);
			String data = GsonUtil.toString(pushData);
			if (data == null) {
				return false;
			}
			MqSend.getInstance().send(mqPush, data);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public boolean sendChitRegister(String phone, String code) {
		return sendChit(ChitData.REGISTER, phone, code);
	}
	public boolean sendChitResetPwd(String phone, String code) {
		return sendChit(ChitData.GET_BACK_PASSWORD, phone, code);
	}
	
	private boolean sendChit(int typeId, String phone, String code) {
		if (mqChit == null || mqChit.length() == 0) {
			return false;
		}
		
		try {
			List<String> listPhone = new ArrayList<String>();
			listPhone.add(phone);
			ChitData chitData = new ChitData(typeId, listPhone, code);
			String data = GsonUtil.toString(chitData);
			if (data == null) {
				return false;
			}
			MqSend.getInstance().send(mqChit, data);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
