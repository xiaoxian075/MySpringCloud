package com.scd.admin.globle.bo;


public class EnvValue {

	private EnvValue() {}

	// 此处使用一个内部类来维护单例
	private static class EnvValueFactory {
		private static EnvValue instance = new EnvValue();
	}

	// 获取实例
	public static EnvValue getInstance() {
		return EnvValueFactory.instance;
	}

	// 如果该对象被用于序列化，可以保证对象在序列化前后保持一致
	public Object readResolve() {
		return getInstance();
	}
		
	
	private String topicAdmin;
	private String topicApp;

	public void setValue(String topicAdmin, String topicApp) {
		this.topicAdmin = topicAdmin;
		this.topicApp = topicApp;
	}

	public String getTopicAdmin() {
		return topicAdmin;
	}

	public String getTopicApp() {
		return topicApp;
	}


}
