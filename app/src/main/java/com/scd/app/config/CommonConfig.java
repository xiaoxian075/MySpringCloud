package com.scd.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scd.app.mgr.FileMgr;
import com.scd.app.mq.MqSend;
import com.scd.app.third.ThirdYft;
import com.scd.app.third.ThirdChit;
import com.scd.app.third.ThirdKd;
import com.scd.sdk.util.Encrypt;
import com.third.jgsdk.JGPush;


@Component
@Order(value=1)
public class CommonConfig implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	

	/**
	 * 环境变量
	 */
    @Value("${encryption.des.password}")
    private String desPassword;

    @Value("${yft.base.url}")
    private String yftBaseUrl;
    @Value("${yft.des.password}")
    private String yftDesPassword;


    @Value("${file.upload.path}")
    private String path;
    @Value("${file.down.baseurl}")
    private String baseUrl;
    @Value("${file.html5.baseurl}")
    private String _html5Url;
    @Value("${file.html5.registerurl}")
    private String _html5RegisterUrl;
    @Value("${file.html5.shareurl}")
    private String _html5Share;
    @Value("${file.html5.jsonurl}")
    private String _html5Json;


	@Value("${third.express.kd100.url}")
    private String kd100Url;
	@Value("${third.express.kd100.key}")
    private String kd100Key;

	@Value("${third.sms.cl.url}")
    private String clUrl;
	@Value("${third.sms.cl.account}")
    private String clAccount;
	@Value("${third.sms.cl.password}")
    private String clPassword;

	@Value("${third.push.jg.mastersecret}")
    private String masterSecret;
	@Value("${third.push.jg.appkey}")
    private String appKey;
	@Value("${third.push.jg.isProduct}")
    private int isProduct;
	
	@Value("${spring.rabbitmq.queue.chit}")
    private String queueChit;
	@Autowired
    private AmqpTemplate rabbitTemplate;


	@Override
	public void run(String... arg0) throws Exception {
//		// 初始化Redis
//		if (template == null) {
//			logger.error("init redis error");
//			return;
//		}
//		RedisUtil.init(template);
		
		// 初始化rabbitmq
		if (rabbitTemplate == null || queueChit == null) {
			logger.error("init rabbitmq error");
			return;
		}
		MqSend.getInstance().init(rabbitTemplate, queueChit);

		// 初始化文件
		if (path == null || baseUrl == null) {
			logger.error("init file upload path error");
			return;
		}
		FileMgr.getInstance().init(path, baseUrl);

		// 初始化DES密钥
		if (desPassword == null) {
			logger.error("init des password error");
			return;
		}
		Encrypt.getInstance().initDespwd(desPassword);

		// 初始化云付通
		if (yftBaseUrl == null || yftDesPassword == null) {
			logger.error("init yft error");
			return;
		}
		ThirdYft.getInstance().init(yftBaseUrl, yftDesPassword);

		// 初始化快递100
		if (kd100Url == null || kd100Key == null) {
			logger.error("init kuai di 100 error");
			return;
		}
		ThirdKd.getInstance().init(kd100Url, kd100Key);

		// 初始化创蓝短信
		if (clUrl == null || clAccount == null || clPassword == null) {
			logger.error("init change lang error");
			return;
		}
		ThirdChit.getInstance().init(clUrl, clAccount, clPassword);

		// 初始化极光
		if (appKey == null || masterSecret == null) {
			logger.error("init ji gong error");
			return;
		}
		boolean bProduct = false;
		if (isProduct == 1) {
			bProduct = true;
		}
		JGPush.getInstance().init(appKey, masterSecret, bProduct);
		
		rootUrl = baseUrl;
		html5Url = _html5Url;
		html5Share = _html5Share;
		html5Json = _html5Json;
		html5RegisterUrl = _html5RegisterUrl;
		
//		List<String> listPhone = new ArrayList<String>();
//		listPhone.add("13255984019");
//		ChitData chitData = new ChitData(1, listPhone, "159874");
//		String data = GsonUtil.toString(chitData);
//		MqSend.getInstance().send(data);
	}
	
	private static String rootUrl;
	private static String html5Url;
	private static String html5Share;
	private static String html5Json;
	private static String html5RegisterUrl;
	
	public static String getRootUrl() {
		return rootUrl;
	}
	
	public static String getHtml5Url() {
		return html5Url;
	}
	public static String getHtml5RegisterUrl() {
		return html5RegisterUrl;
	}

	public static String getHtml5Share() {
		return html5Share;
	}

	public static String getHtml5Json() {
		return html5Json;
	}
}
