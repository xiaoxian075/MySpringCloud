package com.scd.sdk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralValidationUtil {


	/**
	 * 验证手机号码
	 * 
	 * 运营商号段如下：
	 * 中国联通号码：130、131、132、145（无线上网卡）、155、156、185（iPhone5上市后开放）、186、176（4G号段）、
	 *               175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
	 * 中国移动号码：134、135、136、137、138、139、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178
	 * 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719 
	 * 手机号前3位的数字包括：
	 * 1 :1
	 * 2 :3,4,5,7,8
	 * 3 :0,1,2,3,4,5,6,7,8,9 
	 * 总结： 目前java手机号码正则表达式有：
	 * a :"^1[3|4|5|7|8][0-9]\\d{4,8}$"    一般验证情况下这个就可以了
	 * b :"^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
	 * Pattern和Matcher详解（字符串匹配和字节码）http://blog.csdn.net/u010700335/article/details/44616451
	 */
	public static boolean phone(String phone) {
		if (phone == null || phone.length() != 11) {
			return false;
		}

		String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	/**
	 * 验证是否是数字+字母的验证码
	 * @param code
	 * @return
	 */
	public static boolean code(String code, int len) {
		if (code == null) {
			return false;
		}

		//String regex = "[^(A-Z0-9)]";
		String regex = "[0-9]{" + len +"," + len + "}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(code);
		return m.matches();
	}

	/**
	 * 字符、数字、字母任意两种组合，长度为8-20位
	 * @param password
	 * @return
	 */
	public static boolean password(String password, int minLen, int maxLen) {
		if (password == null || minLen<0 || maxLen<minLen) {
			return false;
		}
		//String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{" + minLen + "," + maxLen + "}$";
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![!#$%^&*]+$)[0-9A-Za-z!#$%^&*]{" + minLen + "," + maxLen + "}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(password);
		return m.matches();
	}

	public static boolean name(String name, int minLen, int maxLen) {
		if (name == null || minLen<0 || maxLen<minLen) {
			return false;
		}
		String regex = "[0-9A-Za-z\\u4e00-\\u9fa5]{" + minLen + "," + maxLen + "}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		return m.matches();
	}
	

	


}
