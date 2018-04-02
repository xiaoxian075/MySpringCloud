package com.scd.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.app.constant.Constant;
import com.scd.app.feign.FAccount;
import com.scd.app.feign.FCarousel;
import com.scd.app.pojo.util.CarouseUtil;
import com.scd.app.pojo.vo.CarouselVo;
import com.scd.app.third.ThirdYft;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.sdk.util.MD5Util;
import com.scd.sdk.util.pojo.Return;

@Service
public class MainService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FAccount fAccount;
	
	@Autowired
	private FCarousel fCarousel;
	
	public Return<AccountBo> login(String loginName, String password) {
		password = MD5Util.encodeByMD5(password);
		if (password == null) {
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		AccountBo accountBo = fAccount.getAccountByLoginName(loginName);
		if (accountBo == null) {
			//accountBo = ThirdYft.getInstance().getAccountInfo(loginName, password);
			Return<AccountBo> ret = ThirdYft.getInstance().getAccountInfo(loginName, password);
			if (Return.isErr(ret)) {
				return ret;
			}
			accountBo = ret.getData();
			if (accountBo != null) {
				if (!fAccount.register(accountBo)) {
					return Constant.createReturn(ErrorCom.SYN_ACCOUNT_FAIL);
				}
			} else {
				return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST);
			}
		}
		
		if (!password.equals(accountBo.getPassword())) {
			return Constant.createReturn(ErrorCom.PASSWORD_NOT_MATCH);
		}

		return Constant.createReturn(accountBo);
	}
	
	public Return<CarouselVo> getPics(int type) {
		CarouselVo vo = null;
		try {
			List<CarouselPo> carouselList = fCarousel.findByType(type);
			if (carouselList == null) {
				return Constant.createReturn(ErrorCom.SQL_EXE_ERROR);
			}
			
			vo = CarouseUtil.change(carouselList);
			if (vo == null) {
				return Constant.createReturn(ErrorCom.CHANGE_ERROR);
			}
		} catch (Exception e) {
			vo = null;
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR); 
		}
		
		return Constant.createReturn(vo);
	}
}
