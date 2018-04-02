package com.scd.coredb.feign;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AccountInfoDao;
import com.scd.coredb.dao.CollectDao;
import com.scd.coredb.pojo.db.TAccountInfo;
import com.scd.coredb.pojo.db.TCollect;
import com.scd.coredb.service.AccountService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.bo.AccountBalanceBo;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.bo.CollectBo;
import com.scd.joggle.pojo.param.AccountSelectParam;
import com.scd.joggle.pojo.po.AccountLoginPo;
import com.scd.joggle.pojo.po.AccountPo;
import com.scd.joggle.pojo.po.MemberPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class FAccount {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountInfoDao accountInfoDao;
	@Autowired
	private CollectDao collectDao;
//	@Autowired
//	private PayDao payDao;
	
	@RequestMapping(value = "/getpage")
	public Return<PageInfo<AccountBo>> getpage(@RequestBody AccountSelectParam param) {
		Return<PageInfo<AccountBo>> ret = null;
		try {
			ret = accountService.getpage(param);
    	} catch (Exception e) {
    		logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/register")
	public boolean register(@RequestBody AccountBo accountBo) {
    	if (accountBo == null) {
    		return false;
    	}
    	
    	try {
    		if (!accountService.register(accountBo)) {
    			return false;
    		}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
			return false;
		}
    	
		return true;
	}

	@RequestMapping(value = "/getAccountByLoginName")
	public AccountBo getAccountByLoginName(String loginName) {
		
		if (loginName == null || loginName.length() == 0) {
			return null;
		}
		
		AccountBo accountBo = null;
		try {
			accountBo = accountService.getAccountByLoginName(loginName);
			if (accountBo == null) {
				return null;
			}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
			return null;
		}
		
		return accountBo;
	}

	@RequestMapping(value = "/getInfoByAccount")
	public AccountBo getInfoByAccount(String account) {

		if (account == null || account.length() == 0) {
			return null;
		}

		AccountBo accountBo = null;
		try {
			accountBo = accountService.getAccountByAccount(account);
			if (accountBo == null) {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

		return accountBo;
	}
	
	@RequestMapping(value = "/getAccountBalanceByAccount")
	public AccountBalanceBo getAccountBalanceByAccount(String account) {
		
		if (account == null || account.length() == 0) {
			return null;
		}
		
		AccountBalanceBo accountBalanceBo = null;
		try {
			accountBalanceBo = accountService.getAccountBalanceByAccount(account);
			if (accountBalanceBo == null) {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
		return accountBalanceBo;
	}

    @RequestMapping(value = "/findByLoginName")
    public AccountLoginPo findByLoginName(@RequestParam(value = "loginName") String loginName) {
    	AccountLoginPo po = null;
		try {
			po = accountService.findByLoginName(loginName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		
    	return po;
    }
    
    @RequestMapping(value = "/insertOne")
    public boolean insertOne(@RequestBody AccountLoginPo accountLoginPo) {
       	boolean bReturn = false;
    		try {
    			bReturn = accountService.insertOne(accountLoginPo);
    		} catch (Exception e) {
    			logger.error(e.getMessage());
    			return false;
    		}
        	return bReturn;
    }
    
    @RequestMapping(value = "/resetPwd")
	Return<AccountLoginPo> resetPwd(String loginName, String newPassword) {
    	Return<AccountLoginPo> ret = null;
    		try {
    			ret = accountService.resetPwd(loginName, newPassword);
    		} catch (Exception e) {
    			logger.error(e.getMessage());
    			return Constant.createReturn(ErrorCom.ERROR);
    		}
        	return ret;
    }




	@RequestMapping(value = "/getRefereeList")
	public Return<PageInfo<AccountBo>> getRefereeList(@RequestParam(value = "account") String account,
													  @RequestParam(value = "page") int page,
													  @RequestParam(value = "size") int size) {
		PageInfo<AccountBo> pPage = null;
		try {
			Specification<TAccountInfo> spec = new Specification<TAccountInfo>() {
				@Override
				public Predicate toPredicate(Root<TAccountInfo> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					predicates.add(criteriaBuilder.equal(root.get(TAccountInfo.REFEREE), account));
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			Sort.Order order = new Sort.Order(Sort.Direction.DESC, TAccountInfo.CREATE_TIME);
			Pageable pageable = new PageRequest(page, size, new Sort(order));
			Page<TAccountInfo> pageInfo = accountInfoDao.findAll(spec, pageable);

			if (pageInfo == null) {
				return null;
			}
			List<TAccountInfo> list = pageInfo.getContent();
			int total = (int) pageInfo.getTotalElements();
			if (list == null) {
				return null;
			}
			List<AccountBo> listBo = new ArrayList<AccountBo>();
			for (TAccountInfo tAccountInfo : list) {
				AccountBo accountBo = new AccountBo(
						tAccountInfo.getId(),
						tAccountInfo.getAccount(),
						tAccountInfo.getHeadUrl(),
						tAccountInfo.getLevel(),
						tAccountInfo.getNickName(),
						tAccountInfo.getSex(),
						tAccountInfo.getNickName()
				);
				listBo.add(accountBo);
			}
			pPage = new PageInfo<AccountBo>(page, total, listBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return Constant.createReturn(pPage);
	}

	@RequestMapping(value = "/getCollectList")
	public Return<PageInfo<CollectBo>> getCollectList(@RequestParam(value = "account") String account,
													  @RequestParam(value = "page") int page,
													  @RequestParam(value = "size") int size) {
		PageInfo<CollectBo> pPage = null;
		try {
			Specification<TCollect> spec = new Specification<TCollect>() {
				@Override
				public Predicate toPredicate(Root<TCollect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					predicates.add(criteriaBuilder.equal(root.get(TCollect.ACCOUNT), account));
					predicates.add(criteriaBuilder.equal(root.get(TCollect.STATE), Type.COLLECT_STATE_DOWN));
					return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			Sort.Order order = new Sort.Order(Sort.Direction.DESC, TCollect.CREATE_TIME);
			Pageable pageable = new PageRequest(page, size, new Sort(order));
			Page<TCollect> pageInfo = collectDao.findAll(spec, pageable);
			if (pageInfo == null) {
				return null;
			}
			List<TCollect> list = pageInfo.getContent();
			int total = (int) pageInfo.getTotalElements();
			if (list == null) {
				return null;
			}
			List<CollectBo> listBo = new ArrayList<CollectBo>();
			for (TCollect tCollect : list) {
				CollectBo collectBo = new CollectBo(
						tCollect.getId(),
						tCollect.getAccountId(),
						tCollect.getAccount(),
						tCollect.getCommodityId(),
						tCollect.getCreateTime(),
						tCollect.getCommodityName(),
						tCollect.getCommodityImg(),
						tCollect.getPrice()
				);
				listBo.add(collectBo);
			}
			pPage = new PageInfo<CollectBo>(page, total, listBo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
		return Constant.createReturn(pPage);
	}

	@RequestMapping(value = "/setPayPwd")
	public boolean setPayPwd(@RequestParam(value = "account") String account,
							 @RequestParam(value = "payPwd") String payPwd) {
		if (account == null || payPwd == null || "".equals(payPwd)) {
			return false;
		}
		try {
			accountService.setPayPwd(account,payPwd);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/updatePwd")
	public boolean updatePwd(@RequestParam(value = "account") String account,
							 @RequestParam(value = "newPassword") String newPassword) {
		if (account == null || newPassword == null || "".equals(newPassword)) {
			return false;
		}
		try {
			accountService.updatePwd(account,newPassword);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/updateAccountInfo")
	public Return<AccountPo> updateAccountInfo(@RequestParam(value = "account")String account,
											   @RequestParam(value = "nickName")String nickName,
											   @RequestParam(value = "pic")String pic,
											   @RequestParam(value = "sex")int sex) {
		if (account == null) {
			return Constant.createReturn(ErrorCom.ERROR);
		}

		TAccountInfo byAccount = accountInfoDao.findByAccount(account);
		if(byAccount == null){
			return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
		}
		try {
			if(!"".equals(nickName)){
				byAccount.setNickName(nickName);
			}
			if(!"".equals(pic)){
				byAccount.setHeadUrl(pic);
			}
			if(sex != 0){
				byAccount.setSex(sex);
			}
			AccountPo accountPo = new AccountPo();
			Return<TAccountInfo> ret = accountService.updateAccountInfo(byAccount);
			accountPo.setNickName(ret.getData().getNickName());
			accountPo.setSex(ret.getData().getSex());
			accountPo.setAccount(ret.getData().getAccount());
			accountPo.setHeadUrl(ret.getData().getHeadUrl());
			return Constant.createReturn(accountPo);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
	}


    @RequestMapping(value = "/setState")
    public boolean setState(@RequestParam(value = "id") long id,
                             @RequestParam(value = "type") int type) {
        if (id == 0 || type == 0 ) {
            return false;
        }
        try {
            accountService.setState(id,type);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/getMember")
	public Return<MemberPo> getMember(String account) {
    	Return<MemberPo> ret = null;
		try {
			ret = accountService.getMember(account);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}

		return ret;
    }
//    @RequestMapping(value = "/total")
//    public Return<MemberPo> total(@RequestParam(value = "account") String account) {
//        if (account == null ) {
//            return Constant.createReturn(ErrorCom.ERROR);
//        }
//		MemberPo memberPo = new MemberPo();
//        try {
//			Integer totalBalance = payDao.getTotalConsumeByBalance(account,Type.PAY_ACT_SHOP);
//
//			Integer totalRecharge = payDao.getTotalConsumeBySdk(account,Type.PAY_ACT_RECHARGE,1);
//			Integer totalBalanceByShop = payDao.getTotalConsumeBySdk(account,Type.PAY_ACT_SHOP,1);
//
//			Integer total = totalBalance + totalBalanceByShop;
//			BigDecimal bigTotal = new BigDecimal(total);
//			BigDecimal bigRecharge = new BigDecimal(totalRecharge);
//
//			if(bigTotal.compareTo(new BigDecimal("5000")) >= 0){
//				memberPo.setIsMember(1);
//				memberPo.setCodeInfo(account);
//			}
//			if(bigRecharge.compareTo(new BigDecimal("3000")) >= 0){
//				memberPo.setIsMember(1);
//				memberPo.setCodeInfo(account);
//			}
//			memberPo.setTotalConsume(bigTotal);
//			memberPo.setTotalRecharge(bigRecharge);
//        } catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			return Constant.createReturn(ErrorCom.ERROR);
//        }
//		return Constant.createReturn(memberPo);
//    }
}
