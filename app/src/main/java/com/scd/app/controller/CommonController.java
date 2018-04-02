package com.scd.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.config.CommonConfig;
import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.GetExpAcceptMsg;
import com.scd.app.controller.accept.GetMulExpAcceptMsg;
import com.scd.app.controller.accept.LogisticsAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.bo.RootUrlBo;
import com.scd.app.pojo.vo.AreaVo;
import com.scd.app.pojo.vo.ExpInfoVo;
import com.scd.app.pojo.vo.ExpMulInfoVo;
import com.scd.app.pojo.vo.ShopVo;
import com.scd.app.service.CommonService;
import com.scd.app.third.ThirdKd;
import com.scd.app.third.kd100.Kd100Vo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.AreaShortPojo;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/common")
public class CommonController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CommonService commonService;
	
	/**
	 * 获取URL前缀
	 */
	@RequestMapping(value = "/getRootUrl", method = RequestMethod.POST)
	public String getRootUrl(HttpServletRequest request) {
		RootUrlBo rootUrlBo = null;
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			String url = CommonConfig.getRootUrl();
			String html5Url = CommonConfig.getHtml5Url();
			String html5Json = CommonConfig.getHtml5Json();
			
			rootUrlBo = new RootUrlBo(url, html5Url, html5Json);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(rootUrlBo);
	}
	
	/**
	 * 获取地址列表
	 * @return
	 */
	@RequestMapping(value = "/getAreaChild", method = RequestMethod.POST)
	public String getAreaChild(HttpServletRequest request) {
		List<AreaShortPojo> areaList = null;
		try {
			IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			long parentId = acceptMsg.getId();
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
	
			Return<List<AreaShortPojo>> ret = commonService.getAreaChild(parentId);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			areaList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(areaList);
	}
	
	/**
	 * 获取全部地址列表
	 * @return
	 */
	@RequestMapping(value = "/getArea", method = RequestMethod.POST)
	public String getArea(HttpServletRequest request) {
		List<AreaVo> areaList = null;
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
	
			Return<List<AreaVo>> ret = commonService.getArea();
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			areaList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(areaList);
	}
	
	/**
	 * 获取运费
	 * @return
	 */
	@RequestMapping(value = "/getExp", method = RequestMethod.POST)
	public String getExp(HttpServletRequest request) {
		ExpInfoVo expInfoVo = null;
		try {
			GetExpAcceptMsg acceptMsg = Constant.subPack(request, GetExpAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			long commodityId = acceptMsg.getCommodityId();
			long addressId = acceptMsg.getAddressId();
			Return<ExpInfoVo> ret = commonService.getExp(commodityId, addressId);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			expInfoVo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(expInfoVo);
	}
	
	/**
	 * 获取运费--多个商品合算运费
	 * @return
	 */
	@RequestMapping(value = "/getMulExp", method = RequestMethod.POST)
	public String getMulExp(HttpServletRequest request) {
		ExpMulInfoVo expInfo = null;
		try {
			GetMulExpAcceptMsg acceptMsg = Constant.subPack(request, GetMulExpAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			List<List<Long>> commodityIdList = acceptMsg.getCommodityIdList();
			long addressId = acceptMsg.getAddressId();
			Return<ExpMulInfoVo> ret = commonService.getMulExp(commodityIdList, addressId);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			expInfo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(expInfo);
	}
	
	/**
	 * 查看物流
	 */
	@RequestMapping(value = "/selectLogistics", method = RequestMethod.POST)
	public String selectLogistics(HttpServletRequest request) {
		Kd100Vo kd100Vo = null;
		try {
			LogisticsAcceptMsg acceptMsg = Constant.subPack(request, LogisticsAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkSession(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_SESSION);
			}
			
			String code = acceptMsg.getCode();
			String odd = acceptMsg.getOdd();
			Return<Kd100Vo> ret = ThirdKd.getInstance().queryOne(code, odd);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			kd100Vo = ret.getData();
			if (kd100Vo == null) {
				return Constant.pack(ErrorCom.NOT_EXIST);
			}
			kd100Vo.setName(acceptMsg.getName());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(kd100Vo);
	}
	
	/**
	 * 获取商店轮播图
	 * @param 	shopId	商店ID
	 * @return
	 */
	@RequestMapping(value = "/getShopPics", method = RequestMethod.POST)
	public String getShopPics(HttpServletRequest request) {
		IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		Session session = SessionMgr.getInstance().checkSession(acceptMsg);
		if (session == null) {
			return Constant.pack(ErrorCom.GET_SESSION);
		}
		
		// 根据类型获取轮播图
		long shopId = acceptMsg.getId();
		Return<ShopVo> ret = commonService.getShopPics(shopId);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
}
