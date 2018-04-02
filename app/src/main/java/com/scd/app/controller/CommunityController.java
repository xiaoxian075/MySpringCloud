package com.scd.app.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.GetCommunityListAcceptMsg;
import com.scd.app.controller.accept.HitCommunityAcceptMsg;
import com.scd.app.controller.accept.PraiseCommunityAcceptMsg;
import com.scd.app.controller.receive.HitCommunityReceiveMsg;
import com.scd.app.controller.receive.PraiseCommunityReceiveMsg;
import com.scd.app.mgr.SessionMgr;
import com.scd.app.pojo.vo.CommunityVo;
import com.scd.app.service.CommunityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

/**
 * @ClassName CommunityController
 * @Description 云社区
 * @author chenjx
 * @date 2018-02-06
 */

@RestController
@RequestMapping(value = "/ct")
public class CommunityController {
	
	@Resource
	private CommunityService communityService;
	
	/**
	 * 获取列表
	 * @param 	type	类型 1：学习教育 2：美食分享 3：生活健康 4：健身健美
	 * @return
	 */
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public String getList(HttpServletRequest request) {
		GetCommunityListAcceptMsg acceptMsg = Constant.subPack(request, GetCommunityListAcceptMsg.class);
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
		int page = acceptMsg.getPage();
		int size = acceptMsg.getSize();
		int type = acceptMsg.getType();
		Return<List<CommunityVo>> ret = communityService.getList(session.getAccount(), type, page, size);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
	}
	
	/**
	 * 点击量
	 * @param 	id	项ID
	 * @return
	 */
	@RequestMapping(value = "/hit", method = RequestMethod.POST)
	public String hit(HttpServletRequest request) {
		HitCommunityAcceptMsg acceptMsg = Constant.subPack(request, HitCommunityAcceptMsg.class);
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
		
		
		long id = acceptMsg.getId();
		Return<Long> ret = communityService.hit(id);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		HitCommunityReceiveMsg receiveMsg = new HitCommunityReceiveMsg(id, ret.getData());
		return Constant.pack(receiveMsg);
	}
	
	/**
	 * 点赞量
	 * @param 	id	项ID
	 * @param	type	0:取消点赞 1：点赞
	 * @return
	 */
	@RequestMapping(value = "/praise", method = RequestMethod.POST)
	public String praise(HttpServletRequest request) {
		PraiseCommunityAcceptMsg acceptMsg = Constant.subPack(request, PraiseCommunityAcceptMsg.class);
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
		
		
		long id = acceptMsg.getId();
		int type = acceptMsg.getType();
		Return<Long> ret = communityService.praise(session.getAccount(), id, type);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		PraiseCommunityReceiveMsg receiveMsg = new PraiseCommunityReceiveMsg(id, type, ret.getData());
		return Constant.pack(receiveMsg);
	}
}
