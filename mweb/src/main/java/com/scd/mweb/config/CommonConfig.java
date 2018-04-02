package com.scd.mweb.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.scd.mweb.third.ThirdYft;
import com.scd.mweb.third.ThirdChit;


@Component
@Order(value=1)
public class CommonConfig implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 环境变量
	 */
    
    @Value("${file.down.baseurl}")
    private String _rootUrl;
    @Value("${file.html5.baseurl}")
    private String _html5Url;
    @Value("${file.html5.jsonurl}")
    private String _html5Json;
    
    @Value("${yft.base.url}")
    private String yftBaseUrl;
    @Value("${yft.des.password}")
    private String yftDesPassword;
    

	@Value("${third.sms.cl.url}")
    private String clUrl;
	@Value("${third.sms.cl.account}")
    private String clAccount;
	@Value("${third.sms.cl.password}")
    private String clPassword;

	@Override
	public void run(String... arg0) throws Exception {
		
		// 初始化云付通
		if (yftBaseUrl == null || yftDesPassword == null) {
			logger.error("init yft error");
			return;
		}
		ThirdYft.getInstance().init(yftBaseUrl, yftDesPassword);

		// 初始化创蓝短信
		if (clUrl == null || clAccount == null || clPassword == null) {
			logger.error("init change lang error");
			return;
		}
		ThirdChit.getInstance().init(clUrl, clAccount, clPassword);
		
		rootUrl = _rootUrl;
		html5Url = _html5Url;
		html5Json = _html5Json;
	}
	
	private static String rootUrl;
	private static String html5Url;
	private static String html5Json;
	
	public static String getRootUrl() {
		return rootUrl;
	}
	
	public static String getHtml5Url() {
		return html5Url;
	}

	public static String getHtml5Json() {
		return html5Json;
	}
}
