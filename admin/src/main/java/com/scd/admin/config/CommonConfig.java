package com.scd.admin.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scd.admin.mgr.FileMgr;
import com.scd.joggle.mq.MyMqSend;



@Component
@Order(value=1)
public class CommonConfig implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
//	@Autowired
//    private RedisTemplate<String, String> template;
	
	/**
	 * 环境变量
	 */
    @Value("${file.upload.path}")
    private String fileUploadPath;
    @Value("${file.upload.baseurl}")
    private String fileUploadBaseurl;
	
	@Value("${spring.rabbitmq.queue.chit}")
    private String queueChit;
	@Value("${spring.rabbitmq.queue.push}")
    private String queuePush;
	@Autowired
    private AmqpTemplate rabbitTemplate;

	@Override
	public void run(String... arg0) throws Exception {
		
//		if (	StringUtils.isBlank(topicAdmin) || 
//				StringUtils.isBlank(topicApp)) {
//			logger.error("init env value error");
//			return;
//		}
//		
//		EnvValue.getInstance().setValue(topicAdmin, topicApp);
		
//		// 初始化Redis
//		if (template == null) {
//			logger.error("init redis error");
//			return;
//		}
//		RedisUtil.init(template);
		
//		// 初始化rabbitmq
//		if (rabbitTemplate == null || queueChit == null) {
//			logger.error("init rabbitmq error");
//			return;
//		}
//		Rabbitmq.getInstance().init(rabbitTemplate, queueChit);
		// 初始化rabbitmq
		if (rabbitTemplate == null || queueChit == null || queuePush == null) {
			logger.error("init rabbitmq error");
			return;
		}
		MyMqSend.getInstance().init(rabbitTemplate, queueChit, queuePush);
		
		
		
		// 初始化文件
		if (fileUploadPath == null || fileUploadBaseurl == null) {
			logger.error("init file upload path error");
			return;
		}
		FileMgr.getInstance().init(fileUploadPath, fileUploadBaseurl);
		
	}

}
