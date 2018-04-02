package com.scd.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.TrolleyAddAcceptMsg;
import com.scd.app.controller.accept.TrolleyEditNumAcceptMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.ShopTrolleyVo;
import com.scd.app.pojo.vo.TrolleyVo;
import com.scd.app.service.TrolleyService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/trolley")
public class TrolleyController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private TrolleyService trolleyService;
	
	/**
	 * 获取购物车列表
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		List<ShopTrolleyVo> voList = null;
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_LOGIN);
			}
	
			Return<List<ShopTrolleyVo>> ret = trolleyService.getList(session.getAccount());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			voList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(voList);
	}

	/**
	 * 添加购物车
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		TrolleyVo vo = null;
		try {
			TrolleyAddAcceptMsg acceptMsg = Constant.subPack(request, TrolleyAddAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_LOGIN);
			}
	
			Return<TrolleyVo> ret = trolleyService.add(
					acceptMsg.getOdd(),
					session.getAccount(), 
					acceptMsg.getCommodityId(),
					acceptMsg.getAttrId(),
					acceptMsg.getNum());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			vo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	/**
	 * 修改购买数量
	 */
	@RequestMapping(value = "/editNum", method = RequestMethod.POST)
	public String editNum(HttpServletRequest request) {
		TrolleyVo vo = null;
		try {
			TrolleyEditNumAcceptMsg acceptMsg = Constant.subPack(request, TrolleyEditNumAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_LOGIN);
			}
	
			Return<TrolleyVo> ret = trolleyService.editNum(
					acceptMsg.getId(),
					session.getAccount(),
					acceptMsg.getNum());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			vo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(vo);
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	@RequestMapping(value = "/batchDel", method = RequestMethod.POST)
	public String batchDel(HttpServletRequest request) {
		List<Long> idList = null;
		try {
			IdlistAcceptMsg acceptMsg =Constant.subPack(request, IdlistAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
			Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
			if (session == null) {
				return Constant.pack(ErrorCom.GET_LOGIN);
			}
	
			Return<List<Long>> ret = trolleyService.batchDel(session.getAccount(), acceptMsg.getIdList());
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			idList = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(idList);
	}
}
