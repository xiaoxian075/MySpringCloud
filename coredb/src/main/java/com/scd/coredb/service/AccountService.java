package com.scd.coredb.service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AccountBalanceDao;
import com.scd.coredb.dao.AccountInfoDao;
import com.scd.coredb.dao.AccountLoginDao;
import com.scd.coredb.pojo.db.TAccountBalance;
import com.scd.coredb.pojo.db.TAccountInfo;
import com.scd.coredb.pojo.db.TAccountLogin;
import com.scd.coredb.pojo.util.AccountUtil;
import com.scd.coredb.third.ThirdYft;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBalanceBo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.joggle.pojo.po.MemberPo;
import com.scd.sdk.util.MD5Util;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AccountService {

	@Autowired
	private AccountLoginDao accountLoginDao;

	@Autowired
	private AccountBalanceDao accountBalanceDao;

	@Autowired
	private AccountInfoDao accountInfoDao;
	

	public Return<PageInfo<AccountBo>> getpage(AccountSelectParam param) {
		Specification<TAccountInfo> spec = new Specification<TAccountInfo>() {
    		@Override
    		public Predicate toPredicate(Root<TAccountInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			String account = param.getAccount();
    			if (account != null && account.length() > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TAccountInfo.ACCOUNT).as(String.class), account));
    			}
//    			String odd = param.getOdd();
//    			if (odd != null && odd.length() > 0) {
//    				predicates.add(criteriaBuilder.equal(root.get(TOrder.ODD).as(String.class), odd));
//    			}
//    			int state = param.getState();
//    			if (state > 0) {
//    				predicates.add(criteriaBuilder.equal(root.get(TOrder.STATE).as(Integer.class), state));
//    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		Order order = new Order(Direction.DESC, TAccountInfo.ID);
		Pageable pageable = new PageRequest(param.getPage(), param.getSize(), new Sort(order));
        Page<TAccountInfo> pageInfo = accountInfoDao.findAll(spec, pageable);

        int page = pageInfo.getNumber() + 1;
        long total = pageInfo.getTotalElements();
        List<TAccountInfo> infoList = pageInfo.getContent();
        if (infoList == null) {
        	return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
        }
        
        List<AccountBo> boList = new ArrayList<AccountBo>();
        for (TAccountInfo tAccountInfo : infoList) {
        	String account = tAccountInfo.getAccount();
        	TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
    		if (tAccountBalance == null) {
    			continue;
    		}

    		TAccountLogin tAccountLogin = accountLoginDao.findByAccount(account);
    		if (tAccountLogin == null) {
    			continue;
    		}
    		
    		AccountBo bo = AccountUtil.change(tAccountLogin, tAccountInfo, tAccountBalance);
    		if (bo != null) {
    			boList.add(bo);
    		}
        }
        
        
        PageInfo<AccountBo> kdPage = new PageInfo<AccountBo>(page, total, boList);

        return Constant.createReturn(kdPage);
	}

    public AccountLoginPo findByLoginName(String loginName) {
    	TAccountLogin tAccountLogin = accountLoginDao.findByLoginName(loginName);
    	if (tAccountLogin == null) {
    		return null;
    	}
    	
    	AccountLoginPo po = tAccountLogin.createPo();
		return po;
    }
    
    public boolean insertOne(AccountLoginPo accountLoginPo) {
    	try {
    		TAccountLogin tAccountLogin = TAccountLogin.createNew(accountLoginPo);
    		accountLoginDao.save(tAccountLogin);
    	} catch (Exception e) {
			return false;
		}
    	
		return true;
    }



	@Transactional
	public boolean register(AccountBo accountBo) {

		long curTime = System.currentTimeMillis();

		TAccountLogin tAccountLogin = new TAccountLogin(
				0L,
				accountBo.getLoginName(),
				accountBo.getPassword(),
				accountBo.getAccount(),
				accountBo.getLastLoginTime(),
				curTime,
				curTime
		);
		accountLoginDao.save(tAccountLogin);

		TAccountBalance tAccountBalance = new TAccountBalance(
				0L,
				accountBo.getAccount(),
				accountBo.getBalance(),
				accountBo.getYunBalance(),
				curTime,
				curTime,
				Type.PAY_STATE_USABLE,
				BigDecimal.ZERO,
				BigDecimal.ZERO,
				BigDecimal.ZERO
		);
		accountBalanceDao.save(tAccountBalance);

		TAccountInfo tAccountInfo = new TAccountInfo(
				0L,
				accountBo.getAccount(),
				accountBo.getNickName(),
				accountBo.getHeadUrl(),
				accountBo.getReferee(),
				accountBo.getSex(),
				curTime,
				curTime,
				null,
				Type.IS_PUSH_MSG_USABLE,
				Type.ACCOUNT_STATE_USABLE,
				Type.MEMBER_NORMAL
		);
		accountInfoDao.save(tAccountInfo);

		return true;
	}



	public AccountBo getAccountByLoginName(String loginName) {
		//查本地用户名
		TAccountLogin tAccountLogin = accountLoginDao.findByLoginName(loginName);
		if (tAccountLogin == null) {
			return null;
		}

		String account = tAccountLogin.getAccount();
		if (account == null || account.length() == 0) {
			return null;
		}

		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return null;
		}

		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(account);
		if (tAccountInfo == null) {
			return null;
		}
		
		AccountBo accountBo = AccountUtil.change(tAccountLogin, tAccountInfo, tAccountBalance);
		if (accountBo == null) {
			return null;
		}
		return accountBo;
	}

	public AccountBo getAccountByAccount(String account) {
		if (account == null || account.length() == 0) {
			return null;
		}

		TAccountLogin tAccountLogin = accountLoginDao.findByAccount(account);
		if (tAccountLogin == null) {
			return null;
		}

		//查询用户余额
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return null;
		}
		//查询用户信息
		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(account);
		if (tAccountInfo == null) {
			return null;
		}
		AccountBo accountBo = AccountUtil.change(tAccountLogin, tAccountInfo, tAccountBalance);
		if (accountBo == null) {
			return null;
		}

		return accountBo;
	}
	
	
	
	
	public AccountBalanceBo getAccountBalanceByAccount(String account) {
		if (account == null || account.length() == 0) {
			return null;
		}
		//查询用户余额
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return null;
		}
		AccountBalanceBo accountBalanceBo = new AccountBalanceBo(tAccountBalance.getAccount(), tAccountBalance.getBalance(),tAccountBalance.getTotalEarnings());
		return accountBalanceBo;
	}


	public Return<AccountLoginPo> resetPwd(String loginName, String newPassword) {
		TAccountLogin tAccountLogin = accountLoginDao.findByLoginName(loginName);
		if (tAccountLogin == null) {
			return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
		}
		
		newPassword = MD5Util.encodeByMD5(newPassword);
		tAccountLogin.setPassword(newPassword);
		tAccountLogin.setUpdateTime(System.currentTimeMillis());
		
		TAccountLogin newTAccountLogin = accountLoginDao.save(tAccountLogin);
		if (newTAccountLogin == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		
		AccountLoginPo po = newTAccountLogin.createPo();
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

	public int setPayPwd(String account,String payPwd) {
		return accountInfoDao.updatePayPwd(payPwd,account);
	}

	public int updatePwd(String account, String newPassword) {
		return accountLoginDao.updatePwd(newPassword,account);
	}


	public Return<TAccountInfo> updateAccountInfo(TAccountInfo tAccountInfo) {
		TAccountInfo result = null;
		try {
			result = accountInfoDao.save(tAccountInfo);
		}catch (Exception e){
			log.error(e.getMessage());
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		return Constant.createReturn(result);
	}

    public Return<Object> setState(long id, int type) {
	    TAccountInfo tAccountInfo = accountInfoDao.findById(id);
	    if(tAccountInfo == null){
            return Constant.createReturn(ErrorCom.NOT_EXIST);
        }

	    if(type == 1){
            accountInfoDao.updateState(1, tAccountInfo.getAccount());
        }else if(type == 2){
            accountInfoDao.updateState(0, tAccountInfo.getAccount());
        }else if (type==3){
            accountBalanceDao.updateState(1, tAccountInfo.getAccount());
        }else if(type == 4){
            accountBalanceDao.updateState(0, tAccountInfo.getAccount());
        }
        return Constant.createReturn();
    }

	public Return<MemberPo> getMember(String account) {
		AccountBo accountBo = getAccountByAccount(account);
		if (accountBo == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		int level = accountBo.getLevel();
		if (level == Type.MEMBER_NORMAL) {
			if (ThirdYft.getInstance().checkVip(accountBo.getTotalConsume(), accountBo.getTotalRecharge())) {
				accountBo = dealVip(account);
			}
		}
		
		MemberPo po = new MemberPo(accountBo.getAccount(),accountBo.getLevel(), accountBo.getTotalConsume(), accountBo.getTotalRecharge());
		return Constant.createReturn(po);
	}

	private AccountBo dealVip(String account) {
		if (account == null || account.length() == 0) {
			return null;
		}

		TAccountLogin tAccountLogin = accountLoginDao.findByAccount(account);
		if (tAccountLogin == null) {
			return null;
		}

		//查询用户余额
		TAccountBalance tAccountBalance = accountBalanceDao.findByAccount(account);
		if (tAccountBalance == null) {
			return null;
		}
		//查询用户信息
		TAccountInfo tAccountInfo = accountInfoDao.findByAccount(account);
		if (tAccountInfo == null) {
			return null;
		}
		
		
		
		tAccountInfo.setLevel(Type.MEMBER_VIP);
		tAccountInfo.setUpdateTime(System.currentTimeMillis());
		TAccountInfo newTAccountInfo = accountInfoDao.save(tAccountInfo);
		if (newTAccountInfo == null) {
			return null;
		}
		
		AccountBo accountBo = AccountUtil.change(tAccountLogin, tAccountInfo, tAccountBalance);
		if (accountBo == null) {
			return null;
		}

		return accountBo;
	}
}
