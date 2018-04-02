package com.scd.app.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountVo {

	private long id;
	/**
	 * 账号
	 */
	private String account;

	/**
	 * 登入名
	 */
	private String loginName;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 头像URL
	 */
	private String headUrl;

	/**
	 * 性别  0：无 1：男 2：女
	 */
	private int sex;

	/**
	 * 等级  1：普通会员 2：VIP会员
	 */
	private int level;

	/**
	 * 账户余额
	 */
	private BigDecimal balance;

	/**
	 * 云币
	 */
	private long yunBalance;

	/**
	 * 推荐人
	 */
	private String referee;

	/**
	 * 注册时间
	 */
	private long createTime;

	/**
	 * 最后一次登入时间
	 */
	private long lastLoginTime;

	/**
	 * 支付状态  1：可支付  2：不可支付
	 */
	private int payState;

	/**
	 * 是否推送消息
	 */
	private int isPushMsg;

	/**
	 * 账号状态
	 */
	private int accountState;


	/**
	 * 是否设置支付密码  1：设置  2：没设置
	 */
	private int isSetPayPassword;
}

