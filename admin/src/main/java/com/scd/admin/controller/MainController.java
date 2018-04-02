package com.scd.admin.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scd.admin.constant.Constant;
import com.scd.admin.controller.accept.MnLoginAcceptMsg;
import com.scd.admin.mgr.SessionMgr;
import com.scd.admin.pojo.vo.AdminVo;
import com.scd.admin.pojo.vo.MenuVo;
import com.scd.admin.service.MainService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.pojo.Return;
import com.scd.sdk.util.pojo.Session;
import com.scd.sdk.util.pojo.ZeroAcceptMsg;


@RestController
@RequestMapping(value = "/mn")
public class MainController {
	
	@Resource
	private MainService mainService;
	
	/**
	 * 登入
	 * @param 	request	登入名
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request) {
//		int len = request.getContentLength();
//		ServletInputStream iii = request.getInputStream();
//		byte[] buffer = new byte[len];
//		iii.read(buffer, 0, len);
//		String data = new String(buffer);
//		System.out.println(data);
		
		MnLoginAcceptMsg acceptMsg = Constant.subPack(request, MnLoginAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		if (!acceptMsg.check()) {
			return Constant.pack(ErrorCom.PARSE_ERROR);
		}
		
		String clientIp = request.getRemoteAddr();
		String sessionId = RandomUtil.getUuid();
		if (clientIp == null || clientIp.length() == 0 || sessionId == null || sessionId.length() == 0) {
			return Constant.pack(ErrorCom.ERROR);
		}
		
		
		String loginName = acceptMsg.getLoginName();
		String password = acceptMsg.getPassword();
		Return<AdminVo> ret = mainService.login(loginName, password);
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		AdminVo vo = ret.getData();
		vo.setSessionId(sessionId);
		
		Session session = new Session(clientIp, sessionId, 300, loginName);
		SessionMgr.getInstance().add(session);

		
		return Constant.pack(vo);
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		ZeroAcceptMsg acceptMsg = Constant.subPack(request, ZeroAcceptMsg.class);
		if (acceptMsg == null) {
			return Constant.pack(ErrorCom.UNPACK_ERROR);
		}
		Session session = SessionMgr.getInstance().getSession(acceptMsg);
		if (session != null) {
			SessionMgr.getInstance().remove(session.getSessionId());
		}
		return Constant.pack();
	}
	
	
	@RequestMapping(value = "/menuNav", method = RequestMethod.POST)
	public String menuNav(HttpServletRequest request) {
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
		
		Return<List<MenuVo>> ret = mainService.menuNav();
		if (Return.isErr(ret)) {
			return Constant.pack(ret);
		}
		
		return Constant.pack(ret.getData());
//		return Constant.pack(ErrorCom.GET_LOGIN);
	}
}
//  [{
//  title: "主页",
//  path: "/home",
//  icon: "fa-home"
//}, {
//  title: "表格管理",
//  path: "/table",
//  icon: "fa-table",
//  child: [{
//    title: "基本表格",
//    path: "/table/base"
//  }, {
//    title: "排序表格",
//    path: "/table/sort"
//  }]
//}, {
//  title: "图表管理",
//  path: "/charts",
//  icon: "fa-bar-chart-o",
//  child: [{
//    title: "柱状图表",
//    path: "/charts/bar"
//  }]
//}]
	
	
//	MenuVo home = new MenuVo("主页", "/home", "fa-home", null);
//	
//	List<MenuVo> tableList = new ArrayList<MenuVo>();
//	tableList.add(new MenuVo("基本表格", "/table/base", null, null));
//	tableList.add(new MenuVo("排序表格", "/table/sort", null, null));
//	MenuVo table = new MenuVo("表格管理", "/table", "fa-table", tableList);
//	
//	List<MenuVo> chartsList = new ArrayList<MenuVo>();
//	chartsList.add(new MenuVo("柱状图表", "/charts/bar", null, null));
//	MenuVo charts = new MenuVo("图表管理", "/charts", "fa-bar-chart-o", chartsList);
//	
//	List<MenuVo> listMenu = new ArrayList<MenuVo>();
//	listMenu.add(home);
//	listMenu.add(table);
//	listMenu.add(charts);

