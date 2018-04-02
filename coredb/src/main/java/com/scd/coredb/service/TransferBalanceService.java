package com.scd.coredb.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.coredb.dao.AccountBalanceDao;
import com.scd.coredb.dao.AccountInfoDao;
import com.scd.coredb.dao.FinanceDao;
import com.scd.coredb.dao.PlatformBalanceDao;
import com.scd.coredb.pojo.db.TAccountBalance;
import com.scd.coredb.pojo.db.TAccountInfo;
import com.scd.coredb.pojo.db.TFinance;
import com.scd.coredb.pojo.db.TPlatformBalance;
import com.scd.coredb.third.ThirdYft;
import com.scd.joggle.constant.Type;

@Service
public class TransferBalanceService {
	
	@Autowired
	private AccountBalanceDao accountBalanceDao;
	
	@Autowired
	private AccountInfoDao accountInfoDao;
	
	@Autowired
	private FinanceDao financeDao;
	
	@Autowired
	private PlatformBalanceDao platformBalanceDao;
	
	/**
	 * 处理返现（推荐收益处理）
	 * 推荐人增加收益，消费人增加累计消费 平台支出收益
	 * @param payAccount
	 * @param receiveAccount
	 * @param amount
	 * @return
	 */
	public boolean dealEarnings(String payAccount, String receiveAccount, BigDecimal amount) {
		if (payAccount == null || receiveAccount == null || amount == null) {
			return false;
		}
		
		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(payAccount);
		if (tAccountInfo == null) {
			return false;
		}
		
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(payAccount);
		if (tAccountBalance == null) {
			return true;
		}
		
		String referee = tAccountInfo.getReferee();
		if (referee == null || referee.length() == 0) {
			return true;
		}
		
		TAccountBalance tRefereeBalance = accountBalanceDao.findByAccount(referee);
		if (tRefereeBalance == null) {
			return false;
		}
		
		TPlatformBalance tPlatformBalance = platformBalanceDao.findByAccount(receiveAccount);
		if (tPlatformBalance == null) {
			return false;
		}
		
		BigDecimal earnings = ThirdYft.getInstance().calcEarnings(amount);
		if (earnings == null) {
			return false;
		}
		
		long curTime = System.currentTimeMillis();
		
		// 消费者增加累计消费
		tAccountBalance.setTotalConsume(tAccountBalance.getTotalConsume().add(amount));
		tAccountBalance.setUpdateTime(curTime);
		
		// 平台支出收益
		BigDecimal platformBalance = tPlatformBalance.getBalance();
		platformBalance = platformBalance.subtract(earnings);
		tPlatformBalance.setBalance(platformBalance);
		tPlatformBalance.setUpdateTime(curTime);
		
		// 推荐人增加收益
		BigDecimal refereeBalance = tRefereeBalance.getBalance();
		refereeBalance = refereeBalance.add(earnings);
		tRefereeBalance.setBalance(refereeBalance);
		BigDecimal totalEarnings = tRefereeBalance.getTotalEarnings();
		totalEarnings = totalEarnings.add(earnings);
		tRefereeBalance.setTotalEarnings(totalEarnings);
		tRefereeBalance.setUpdateTime(curTime);

		TAccountBalance newTAccountBalance = accountBalanceDao.save(tAccountBalance);
		if (newTAccountBalance == null) {
			return false;
		}
		
		TPlatformBalance newTPlatformBalance = platformBalanceDao.save(tPlatformBalance);
		if (newTPlatformBalance == null) {
			return false;
		}
		
		TAccountBalance newTRefereeBalance = accountBalanceDao.save(tRefereeBalance);
		if (newTRefereeBalance == null) {
			return false;
		}
		
		TFinance tFinance = new TFinance(
				0,
				referee,
				Type.FINANCE_TYPE_INCOME,
				Type.FINANCE_SUBJECT_EARNINGS,
				earnings,
				curTime
				);
		TFinance newTFinance = financeDao.save(tFinance);
		if (newTFinance == null) {
			return false;
		}
		
		tFinance = new TFinance(
				0,
				receiveAccount,
				Type.FINANCE_TYPE_OUTCOME,
				Type.FINANCE_SUBJECT_EARNINGS,
				earnings,
				curTime
				);
		newTFinance = financeDao.save(tFinance);
		if (newTFinance == null) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 余额消费账户资金处理
	 * @param payAccount
	 * @param receiveAccount
	 * @param amount
	 * @return
	 */
	public boolean transferBalance(String payAccount, String receiveAccount, BigDecimal amount) {
		if (payAccount == null || receiveAccount == null || amount == null) {
			return false;
		}
		
		
		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(payAccount);
		if (tAccountInfo == null) {
			return false;
		}
		
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(payAccount);
		if (tAccountBalance == null) {
			return false;
		}
		
		
		TPlatformBalance tPlatformBalance = platformBalanceDao.findByAccount(receiveAccount);
		if (tPlatformBalance == null) {
			return false;
		}
		
		BigDecimal receiveBalance = tPlatformBalance.getBalance();
		BigDecimal payBalance = tAccountBalance.getBalance();
		if (payBalance.compareTo(amount) < 0) {
			return false;
		}

		payBalance = payBalance.subtract(amount);
		receiveBalance = receiveBalance.add(amount);
		
		long curTime = System.currentTimeMillis();
		
		tAccountBalance.setBalance(payBalance);
		
		tAccountBalance.setUpdateTime(curTime);
		TAccountBalance newTAccountBalance = accountBalanceDao.save(tAccountBalance);
		if (newTAccountBalance == null) {
			return false;
		}
		
		tPlatformBalance.setBalance(receiveBalance);
		tPlatformBalance.setUpdateTime(curTime);
		TPlatformBalance newTPlatformBalance = platformBalanceDao.save(tPlatformBalance);
		if (newTPlatformBalance == null) {
			return false;
		}
		
		TFinance tFinance = new TFinance(
				0,
				payAccount,
				Type.FINANCE_TYPE_OUTCOME,
				Type.FINANCE_SUBJECT_BUY,
				amount,
				curTime
				);
		TFinance newTFinance = financeDao.save(tFinance);
		if (newTFinance == null) {
			return false;
		}
		
		tFinance = new TFinance(
				0,
				receiveAccount,
				Type.FINANCE_TYPE_INCOME,
				Type.FINANCE_SUBJECT_BUY,
				amount,
				curTime
				);
		newTFinance = financeDao.save(tFinance);
		if (newTFinance == null) {
			return false;
		}
		
		return true;
	}

	/**
	 * 充值加余额处理
	 * @param account
	 * @param amount
	 * @return
	 */
	public boolean addAmountToBalance(String account, BigDecimal amount) {
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return false;
		}
		long curTime = System.currentTimeMillis();
		
		BigDecimal balance = tAccountBalance.getBalance();
		balance = balance.add(amount);
		tAccountBalance.setBalance(balance);
		tAccountBalance.setTotalRecharge(tAccountBalance.getTotalRecharge().add(amount));
		tAccountBalance.setUpdateTime(curTime);
		TAccountBalance newTAccountBalance = accountBalanceDao.save(tAccountBalance);
		if (newTAccountBalance == null) {
			return false;
		}
		
		TFinance tFinance = new TFinance(
				0,
				account,
				Type.FINANCE_TYPE_INCOME,
				Type.FINANCE_SUBJECT_RECHANGE,
				amount,
				curTime
				);
		TFinance newTFinance = financeDao.save(tFinance);
		if (newTFinance == null) {
			return false;
		}
		
		return true;
	}
}
