package com.scd.admin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.CommodityAcceptMsg;
import com.scd.admin.controller.accept.CommodityAttrAcceptMsg;
import com.scd.admin.controller.accept.CommodityPageAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.util.CommodityUtil;
import com.scd.admin.pojo.vo.CommodityAttrVo;
import com.scd.admin.pojo.vo.CommodityVo;
import com.scd.admin.service.CommodityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.param.CommodityAttrParam;
import com.scd.joggle.pojo.param.CommodityParam;
import com.scd.joggle.pojo.param.CommoditySelectParam;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdPageAcceptMsg;
import com.scd.sdk.util.pojo.IdStateAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;

@RestController
@RequestMapping(value = "/commodity")
public class CommodityController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private CommodityService commodityService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(HttpServletRequest request) {
		PageInfo<CommodityVo> pageInfo = null;
		try {
			CommodityPageAcceptMsg acceptMsg = Constant.subPack(request, CommodityPageAcceptMsg.class);
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
	
			long shopId = acceptMsg.getShopId();
			int shopProductType = acceptMsg.getShopProductType();
			int state = acceptMsg.getState();
			int page = acceptMsg.getPage();
			int size = acceptMsg.getSize();
			CommoditySelectParam param = new CommoditySelectParam(shopId, shopProductType, state, page, size);
			Return<PageInfo<CommodityVo>> ret = commodityService.list(param);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			pageInfo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(pageInfo);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest request) {
		CommodityVo vo = null;
		try {
			CommodityAcceptMsg acceptMsg = Constant.subPack(request, CommodityAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getAddrId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
			
			CommodityParam param = CommodityUtil.change(acceptMsg);
			if (param == null) {
				return Constant.pack(ErrorCom.CHANGE_ERROR);
			}
			Return<CommodityVo> ret = commodityService.add(param);
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
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(HttpServletRequest request) {
		CommodityVo vo = null;
		try {
			CommodityAcceptMsg acceptMsg = Constant.subPack(request, CommodityAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
			
			CommodityParam param = CommodityUtil.change(acceptMsg);
			if (param == null) {
				return Constant.pack(ErrorCom.CHANGE_ERROR);
			}
			Return<CommodityVo> ret = commodityService.edit(param);
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
	
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public String del(HttpServletRequest request) {
		long returnId = 0;
		try {
			IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
			long id = acceptMsg.getId();
			Return<Long> ret = commodityService.del(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			returnId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(returnId);
	}
	
	@RequestMapping(value = "/batchDel", method = RequestMethod.POST)
	public String batchDel(HttpServletRequest request) {
		List<Long> listId = null;
		try {
			IdlistAcceptMsg acceptMsg = Constant.subPack(request, IdlistAcceptMsg.class);
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
	
			List<Long> idList = acceptMsg.getIdList();
			Return<List<Long>> ret = commodityService.batchDel(idList);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			listId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(listId);
	}
	
	@RequestMapping(value = "/setState", method = RequestMethod.POST)
	public String setState(HttpServletRequest request) {
		CommodityVo vo = null;
		try {
			IdStateAcceptMsg acceptMsg = Constant.subPack(request, IdStateAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (!Type.checkCommodityState(acceptMsg.getState())) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
			Return<CommodityVo> ret = commodityService.setState(acceptMsg.getId(), acceptMsg.getState());
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
	
	

	@RequestMapping(value = "/attrList", method = RequestMethod.POST)
	public String attrList(HttpServletRequest request) {
		PageInfo<CommodityAttrVo> pageInfo = null;
		try {
			IdPageAcceptMsg acceptMsg = Constant.subPack(request, IdPageAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
			long commodityId = acceptMsg.getId();
			int page = acceptMsg.getPage();
			int size = acceptMsg.getSize();
			Return<PageInfo<CommodityAttrVo>> ret = commodityService.attrList(commodityId, page, size);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			pageInfo = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(pageInfo);
	}
	
	@RequestMapping(value = "/attrAdd", method = RequestMethod.POST)
	public String attrAdd(HttpServletRequest request) {
		CommodityAttrVo vo = null;
		try {
			CommodityAttrAcceptMsg acceptMsg = Constant.subPack(request, CommodityAttrAcceptMsg.class);
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
			
			CommodityAttrParam param = CommodityUtil.change(acceptMsg);
			if (param == null) {
				return Constant.pack(ErrorCom.CHANGE_ERROR);
			}
			Return<CommodityAttrVo> ret = commodityService.attrAdd(param);
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
	
	@RequestMapping(value = "/attrEdit", method = RequestMethod.POST)
	public String attrEdit(HttpServletRequest request) {
		CommodityAttrVo vo = null;
		try {
			CommodityAttrAcceptMsg acceptMsg = Constant.subPack(request, CommodityAttrAcceptMsg.class);
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
			
			CommodityAttrParam param = CommodityUtil.change(acceptMsg);
			if (param == null) {
				return Constant.pack(ErrorCom.CHANGE_ERROR);
			}
			Return<CommodityAttrVo> ret = commodityService.attrEdit(param);
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
	
	@RequestMapping(value = "/attrDel", method = RequestMethod.POST)
	public String attrDel(HttpServletRequest request) {
		long returnId = 0;
		try {
			IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			if (!acceptMsg.check()) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			if (acceptMsg.getId() <= 0) {
				return Constant.pack(ErrorCom.PARSE_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
			long id = acceptMsg.getId();
			Return<Long> ret = commodityService.attrDel(id);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			returnId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(returnId);
	}
	
	@RequestMapping(value = "/attrBatchDel", method = RequestMethod.POST)
	public String attrBatchDel(HttpServletRequest request) {
		List<Long> listId = null;
		try {
			IdlistAcceptMsg acceptMsg = Constant.subPack(request, IdlistAcceptMsg.class);
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
	
			List<Long> idList = acceptMsg.getIdList();
			Return<List<Long>> ret = commodityService.attrBatchDel(idList);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			listId = ret.getData();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(ErrorCom.ERROR);
		}
		
		return Constant.pack(listId);
	}
}
