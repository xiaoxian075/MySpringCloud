package com.scd.coredb.pojo.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.scd.joggle.pojo.po.AccountLoginPo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_account_login")
public class TAccountLogin {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	/**
	 * 登入名
	 */
	@Column(name = "login_name", nullable = false, length = 64)
	private String loginName;
	
	/**
	 * 登入密码
	 */
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	
	/**
	 * 账号
	 */
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	/**
	 * 最后一次登入时间
	 */
	@Column(name = "last_login_time", nullable = false, length = 20)
	private long lastLoginTime;
	
	/**
	 * 创建时间
	 */
	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
	/**
	 * 更新时间
	 */
	@Column(name = "update_time", nullable = false, length = 20)
	private long updateTime;
	

//	public TAccountLogin(String loginName, String password, String account, long curTime) {
//		this.id = 0;
//		this.loginName = loginName;
//		this.password = password;
//		this.account = account;
//		this.lastLoginTime = curTime;
//		this.createTime = curTime;
//		this.updateTime = curTime;
//	}
	
	public AccountLoginPo createPo() {
		return new AccountLoginPo(
				id,
				loginName,
				password,
				account,
				lastLoginTime,
				createTime,
				updateTime
				);
	}
	public static TAccountLogin createNew(AccountLoginPo accountLoginPo) {
		return new TAccountLogin(
				accountLoginPo.getId(),
				accountLoginPo.getLoginName(),
				accountLoginPo.getPassword(),
				accountLoginPo.getAccount(),
				accountLoginPo.getLastLoginTime(),
				accountLoginPo.getCreateTime(),
				accountLoginPo.getUpdateTime());
	}
}

