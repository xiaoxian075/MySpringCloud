package com.scd.coredb.pojo.db;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_finance")
public class TFinance {

	public final static String ID = "id";
	public final static String ACCOUNT = "account";
	public final static String TYPE = "type";
	public final static String SUBJECT = "subject";
	public final static String AMOUNT = "amount";
	public final static String CREATE_TIME = "createTime";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false, length = 20)
	private long id;
	
	@Column(name = "account", nullable = false, length = 64)
	private String account;
	
	@Column(name = "type", nullable = false, length = 11)
	private int type;
	
	@Column(name = "subject", nullable = false, length = 11)
	private int subject;
	
	@Digits(integer=11, fraction=2)
	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "create_time", nullable = false, length = 20)
	private long createTime;
	
}
