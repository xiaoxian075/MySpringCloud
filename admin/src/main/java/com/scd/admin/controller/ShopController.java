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
import com.scd.admin.controller.accept.ShopAddAcceptMsg;
import com.scd.admin.controller.accept.ShopEditAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.ShopVo;
import com.scd.admin.service.ShopService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.pojo.IdAcceptMsg;
import com.scd.sdk.util.pojo.IdlistAcceptMsg;
import com.scd.sdk.util.pojo.PageAcceptMsg;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;

@RestController
@RequestMapping(value = "/shop")
public class ShopController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private ShopService shopService;
	
	/**
	 * 获取地址列表
	 * @return
	 */
	@RequestMapping(value = "/getAllShop", method = RequestMethod.POST)
	public String getAllShop(HttpServletRequest request) {
		List<ShopVo> areaList = null;
		try {
			ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
			if (acceptMsg == null) {
				return Constant.pack(ErrorCom.UNPACK_ERROR);
			}
			
		      Session session = SessionMgr.getInstance().checkLogin(acceptMsg);
		      if (session == null) {
		          return Constant.pack(ErrorCom.GET_LOGIN);
		      }
	
			Return<List<ShopVo>> ret = shopService.getAllShop();
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
	
	 @RequestMapping(value = "/list", method = RequestMethod.POST)
	    public String list(HttpServletRequest request) {
	        PageAcceptMsg acceptMsg = Constant.subPack(request, PageAcceptMsg.class);
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

	        int page = acceptMsg.getPage();
	        int size = acceptMsg.getSize();
	        Return<PageInfo<ShopVo>> ret = shopService.list(page, size);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }

	        return Constant.pack(ret.getData());
	    }

	    @RequestMapping(value = "/add", method = RequestMethod.POST)
	    public String add(HttpServletRequest request) {
	        ShopAddAcceptMsg acceptMsg = Constant.subPack(request, ShopAddAcceptMsg.class);
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

	        String name = acceptMsg.getName();
	        List<String> listPic = acceptMsg.getListPic();
	        Return<Object> ret = shopService.add(name, listPic);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }

	        return Constant.pack();
	    }

	    @RequestMapping(value = "/edit", method = RequestMethod.POST)
	    public String edit(HttpServletRequest request) {
	        ShopEditAcceptMsg acceptMsg = Constant.subPack(request, ShopEditAcceptMsg.class);
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
	        String name = acceptMsg.getName();
	        List<String> listPic = acceptMsg.getListPic();
	        Return<Object> ret = shopService.edit(id, name, listPic);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }

	        return Constant.pack();
	    }

	    @RequestMapping(value = "/del", method = RequestMethod.POST)
	    public String del(HttpServletRequest request) {
	        IdAcceptMsg acceptMsg = Constant.subPack(request, IdAcceptMsg.class);
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
	        Return<Long> ret = shopService.del(id);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }

	        return Constant.pack(ret.getData());
	    }

	    @RequestMapping(value = "/batchDel", method = RequestMethod.POST)
	    public String batchDel(HttpServletRequest request) {
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
	        Return<List<Long>> ret = shopService.batchDel(idList);
	        if (Return.isErr(ret)) {
	            return Constant.pack(ret);
	        }

	        return Constant.pack(ret.getData());
	    }
}
