//package com.scd.sdk.redis;
//
//import java.lang.reflect.Type;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.lang3.StringUtils;
////import org.apache.commons.lang3.StringUtils;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//
//import com.scd.sdk.util.GsonUtil;
//
//
///**
// * @ClassName 
// * @Description 
// * @author chenjx
// * @date 2017年10月16日 上午9:57:51
// */
//public class RedisUtil {
//
//	private static RedisTemplate<String, String> template;
//	
//	public static void init(RedisTemplate<String, String> inTemplate) {
//		template = inTemplate;
//	}
//	
//    public static boolean sendMessage(String channel, String message) {
//    	if (channel == null || message == null || channel.length()==0) {
//    		return false;
//    	}
//		template.convertAndSend(channel, message);
//		return true;
//    }
//    
//    public static <T> boolean sendMessage(RedisBo<T> nRedis) {
//		if (nRedis == null) {
//			return false;
//		}
//		String channel = nRedis.getTopic();
//		String data = GsonUtil.toString(nRedis);
//		if (channel == null || data == null) {
//			return false;
//		}
//		
//		template.convertAndSend(channel, data);
//		return true;
//	} 
//	
//    
//    public static String get(String key) {
//    	if (StringUtils.isBlank(key)) {
//    		return null;
//    	}
//    	
//    	ValueOperations<String, String> vo = template.opsForValue();
//    	if (vo == null) {
//    		return null;
//    	}
//    	String data = vo.get(key);
//    	if (data == null) {
//    		return null;
//    	}
//    	
//    	return data;
//    }
//    
//    public static <T> T get(String key, Type c) {
//    	if (StringUtils.isBlank(key) || c == null) {
//    		return null;
//    	}
//    	
//    	ValueOperations<String, String> vo = template.opsForValue();
//    	if (vo == null) {
//    		return null;
//    	}
//    	String data = vo.get(key);
//    	if (data == null) {
//    		return null;
//    	}
//    	
//    	T t = GsonUtil.toJson(data, c);
//    	if (t == null) {
//    		return null;
//    	}
//    	
//    	return t;
//    }
//    
//    
//    public static boolean set(String key, String value) {
//    	return set(key, value, -1);
//    }
//    public static boolean set(String key, String value, long timeOut) {
//    	if (StringUtils.isBlank(key) || value == null || timeOut<-1) {
//    		return false;
//    	}
//    	
//    	ValueOperations<String, String> vo = template.opsForValue();
//    	if (vo == null) {
//    		return false;
//    	}
//
//    	if (timeOut<1) {
//    		vo.set(key, value);
//    	} else {
//    		vo.set(key, value, timeOut, TimeUnit.SECONDS);
//    	}
//		
//		return true;
//    }
//    
//    public static <T> boolean set(String key, T t) {
//    	return set(key, t, -1);
//    }
//    public static <T> boolean set(String key, T t, long timeOut) {
//    	if (StringUtils.isBlank(key) || t == null || timeOut<-1) {
//    		return false;
//    	}
//    	
//    	String data = GsonUtil.toString(t);
//    	if (data == null) {
//    		return false;
//    	}
//		
//    	ValueOperations<String, String> vo = template.opsForValue();
//    	if (vo == null) {
//    		return false;
//    	}
//    	
//    	if (timeOut<1) {
//    		vo.set(key, data);
//    	} else {
//    		vo.set(key, data, timeOut, TimeUnit.SECONDS);
//    	}
//		
//		return true;
//    }
//
//	
//}
//
