package com.scd.sdk.util;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);
	
	public static String httpGet(String url) {
		return http(url, HttpMethod.GET, "");
	}
	
	public static String httpGet(String url, String body) {
		if (body == null) {
			body = "";
		}
		return http(url, HttpMethod.GET, body);
	}
	
	public static String httpPost(String url) {
		return http(url, HttpMethod.POST, "");
	}
	
	public static String httpPost(String url, String body) {
		if (body == null) {
			body = "";
		}
		return http(url, HttpMethod.POST, body);
	}
	

	
	public static String httpPost(String url, Map<String, String> paramsMap) {
		String body = "";
		if (paramsMap != null && paramsMap.size() > 0) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				body += entry.getKey() + "=" + entry.getValue() + "&";
			}
			body = body.substring(0, body.length() - 1);
		}
		return http(url, HttpMethod.POST, body);
	}
	
	public static String httpPostFprYft(String url, Map<String, String> paramsMap) {
		String body = "";
		if (paramsMap != null && paramsMap.size() > 0) {
			for (Entry<String, String> entry : paramsMap.entrySet()) {
				body += entry.getKey() + "=" + entry.getValue() + "&";
			}
			body = body.substring(0, body.length() - 1);
		}
		
		url = url + "?" + body;
		return http(url, HttpMethod.POST, "");
	}
//	
//	/**
//	 * 基于HttpClient 4.3的通用POST方法
//	 *
//	 * @param url       提交的URL
//	 * @param paramsMap 提交<参数，值>Map
//	 * @return 提交响应
//	 */
//
//	public static String commonPost(String url, Map<String, String> paramsMap) {
//		CloseableHttpClient client = HttpClients.createDefault();
//		String responseText = "";
//		CloseableHttpResponse response = null;
//		try {
//			HttpPost method = new HttpPost(url);
//			if (paramsMap != null) {
//				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
//					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
//					paramList.add(pair);
//				}
//				method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
//			}
//			response = client.execute(method);
//			org.apache.http.HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				responseText = EntityUtils.toString(entity);
//			}
//		} catch (Exception e) {
//			//e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				response.close();
//			} catch (Exception e) {
//				//e.printStackTrace();
//				return null;
//			}
//		}
//		return responseText;
//	}
//	
//	public static String commonGet(String url) {
//		CloseableHttpClient client = HttpClients.createDefault();
//		String responseText = "";
//		CloseableHttpResponse response = null;
//		try {
//			HttpGet method = new HttpGet(url);
//			response = client.execute(method);
//			org.apache.http.HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				responseText = EntityUtils.toString(entity);
//			}
//		} catch (Exception e) {
//			//e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				response.close();
//			} catch (Exception e) {
//				//e.printStackTrace();
//				return null;
//			}
//		}
//		return responseText;
//	}
	
	private static String http(String url, HttpMethod method, String body) {
		String data = null;
		try {
			RestTemplate restTemplate=new RestTemplate();
			// 注意：必须 http、https……开头，不然报错，浏览器地址栏不加 http 之类不出错是因为浏览器自动帮你补全了
			HttpHeaders headers = new HttpHeaders();
			// 这个对象有add()方法，可往请求头存入信息       
			headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
			//headers.setContentType("ContentType","application/x-www-form-urlencoded;charset=UTF-8");
			// 解决中文乱码的关键 , 还有更深层次的问题 关系到 StringHttpMessageConverter，先占位，以后补全
			HttpEntity<String> entity = new HttpEntity<String>(body, headers);
			
			
			/* body是Http消息体例如json串 */ 
			ResponseEntity<String> response = restTemplate.exchange(url, method, entity, String.class);
			data = response.getBody();
			
		} catch(Exception e) {
			LOGGER.error(e.getMessage());
			return null;
		}
		
		return data;
	}
	
	/**
	 * 基于HttpClient 4.3的通用POST方法
	 *
	 * @param url       提交的URL
	 * @param paramsMap 提交<参数，值>Map
	 * @return 提交响应
	 */

//	public static String commonPost(String url, Map<String, String> paramsMap) {
//		CloseableHttpClient client = HttpClients.createDefault();
//		String responseText = "";
//		CloseableHttpResponse response = null;
//		try {
//			HttpPost method = new HttpPost(url);
//			if (paramsMap != null) {
//				List<NameValuePair> paramList = new ArrayList<NameValuePair>();
//				for (Map.Entry<String, String> param : paramsMap.entrySet()) {
//					NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
//					paramList.add(pair);
//				}
//				method.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
//			}
//			response = client.execute(method);
//			org.apache.http.HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				responseText = EntityUtils.toString(entity);
//			}
//		} catch (Exception e) {
//			//e.printStackTrace();
//			return null;
//		} finally {
//			try {
//				response.close();
//			} catch (Exception e) {
//				//e.printStackTrace();
//				return null;
//			}
//		}
//		return responseText;
//	}
	
}

