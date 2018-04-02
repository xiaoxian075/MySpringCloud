package com.scd.sdk.util;

import java.util.Random;

public class GeneralProduceUtil {
	
	private static final char[] CHARS={'1','2','3','4','5','6','7','8','9','0'};
	//private static final char[] CHARS={'1','2','3','4','5','6','7','8','9','0','Q','W','E','R','T','Y','U','I','O','P','A','S','D','F','G','H','J','K','L','Z','X','C','V','B','N','M'};

	private static Random random=new Random();
	
	/**
	 * 产生一个验证码
	 * @return
	 */
	public static String createCode() {
        StringBuffer sb=new StringBuffer();
        for(int i = 0; i < 6; i++){
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
	}
}
