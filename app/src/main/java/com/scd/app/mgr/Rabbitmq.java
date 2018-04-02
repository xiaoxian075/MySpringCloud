//package com.scd.app.mgr;
//
//import com.scd.joggle.chit.ChitData;
//import com.scd.sdk.util.GsonUtil;
//import org.springframework.amqp.core.AmqpTemplate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class Rabbitmq {
//
//	private Rabbitmq() {
//	}
//	private static class RabbitmqFactory {
//		private static Rabbitmq instance = new Rabbitmq();
//	}
//	public static Rabbitmq getInstance() {
//		return RabbitmqFactory.instance;
//	}
//	
//	private AmqpTemplate template;
//    private String queueChit;
//	
//	public void init(AmqpTemplate template, String queueChit) {
//		this.template = template;
//		this.queueChit = queueChit;
//	}
//	
//	private boolean send(String queue, String data) {
//		if (queue == null || data == null || data.length() == 0) {
//    		return false;
//    	}
//		try {
//			template.convertAndSend(queue, data);
//		} catch(Exception e) {
//			return false;
//		}
//		return true;
//	}
//	
//	
//    
//    
//    
//    public boolean sendChitMsg(String data) {
//    	return send(queueChit, data);
//    }
//
//    /**
//     * 发送验证码之注册
//     * @param phone
//     * @param code
//     * @return
//     */
//	public boolean sendChitRegister(String phone, String code) {
//		List<String> phoneList = new ArrayList<String>();
//		phoneList.add(phone);
//		ChitData chitData = new ChitData(ChitData.REGISTER, phoneList, code);
//		String data = GsonUtil.toString(chitData);
//		if (data == null) {
//			return false;
//		}
//		return send(queueChit, data);
//	}
//	
//    /**
//     * 发送验证码之找回密码
//     * @param phone
//     * @param code
//     * @return
//     */
//	public boolean sendChitGetBackPassword(String phone, String code) {
//		List<String> phoneList = new ArrayList<String>();
//		phoneList.add(phone);
//		ChitData chitData = new ChitData(ChitData.GET_BACK_PASSWORD, phoneList, code);
//		String data = GsonUtil.toString(chitData);
//		if (data == null) {
//			return false;
//		}
//		return send(queueChit, data);
//	}
//}
