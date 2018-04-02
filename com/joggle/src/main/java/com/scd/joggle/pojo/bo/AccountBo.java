package com.scd.joggle.pojo.bo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountBo {

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
	 * 密码 （MD5加密后的）
	 */
	private String password;

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
	 * 支付密码
	 */
	private String payPassword;

	/**
	 * 是否推送消息
	 */
	private int isPushMsg;

	/**
	 * 账号状态
	 */
	private int accountState;
	
	/**
	 * 总消费
	 */
    private BigDecimal totalConsume;
    
    /**
     * 总充值
     */
    private BigDecimal totalRecharge;

	public AccountBo() {
	}
	public AccountBo(long id, String account, String headUrl, int level, String nickName, int sex,String loginName) {
		this.id = id;
		this.account = account;
		this.headUrl = headUrl;
		this.level = level;
		this.nickName = nickName;
		this.sex = sex;
		this.loginName = loginName;
	}

	public AccountBo(long id, String account, String loginName, String password, String nickName, String headUrl, int sex, int level, BigDecimal balance, long yunBalance, String referee, long createTime, long lastLoginTime, int payState, String payPassword, int isPushMsg, int accountState, BigDecimal totalConsume, BigDecimal totalRecharge) {
		this.id = id;
		this.account = account;
		this.loginName = loginName;
		this.password = password;
		this.nickName = nickName;
		this.headUrl = headUrl;
		this.sex = sex;
		this.level = level;
		this.balance = balance;
		this.yunBalance = yunBalance;
		this.referee = referee;
		this.createTime = createTime;
		this.lastLoginTime = lastLoginTime;
		this.payState = payState;
		this.payPassword = payPassword;
		this.isPushMsg = isPushMsg;
		this.accountState = accountState;
		this.totalConsume = totalConsume;
		this.totalRecharge = totalRecharge;
	}
}
