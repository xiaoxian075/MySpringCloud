package com.scd.joggle.chit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 短信通用消息
 * @author chenjx
 *
 */

@Data
@AllArgsConstructor
public class ChitData {
	
	// 获取验证码之注册
	public final static int REGISTER = 1;
	// 获取验证码之找回密码
	public final static int GET_BACK_PASSWORD = 2;
	
	
	/**
	 * 编号
	 * 1：获取验证码之注册
	 * 2：获取验证码之找回密码
	 */
	private int id;
	/**
	 * 发送手机列表
	 */
	private List<String> phoneList;
	/**
	 * 发送内容
	 */
	private String content;		
}
