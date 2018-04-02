package com.scd.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.admin.constant.Constant;
import com.scd.admin.dao.AdminDao;
import com.scd.admin.dao.MenuDao;
import com.scd.admin.dao.pojo.TAdmin;
import com.scd.admin.dao.pojo.TMenu;
import com.scd.admin.mgr.FileMgr;
import com.scd.admin.pojo.util.MenuUtil;
import com.scd.admin.pojo.vo.AdminVo;
import com.scd.admin.pojo.vo.MenuVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.sdk.util.MD5Util;
import com.scd.sdk.util.pojo.Return;

@Service
public class MainService {
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private MenuDao menuDao;

	public Return<AdminVo> login(String loginName, String password) {
		
		TAdmin tAdmin = adminDao.findByLoginName(loginName);
		if (tAdmin == null) {
			return Constant.createReturn(ErrorCom.LOGIN_NAME_NOT_EXIST); 
		}
		
		password = MD5Util.encodeByMD5(password);
		if (!password.equals(tAdmin.getPassword())) {
			return Constant.createReturn(ErrorCom.PASSWORD_NOT_MATCH);
		}
		
		List<String> menuList = new ArrayList<String>();
		Iterator<TMenu> menuIter = menuDao.findAll().iterator();
		while (menuIter.hasNext()) {
			TMenu tMenu = menuIter.next();
			if (tMenu != null) {
				String name = tMenu.getName();
				if (name != null && name.trim().length() > 0) {
					menuList.add(name);
				}
			}
		}
		
		//AdminVo adminVo = AdminUtil.change(tAdmin, menuList);
		AdminVo vo = new AdminVo(
				tAdmin.getLoginName(),
				tAdmin.getUserName(),
				tAdmin.getHeadUrl(),
				tAdmin.getAge(),
				tAdmin.getDesc()
				);
		vo.setMenu(menuList);
		vo.setBaseUrl(FileMgr.getInstance().getBaseUrl());
		
		return Constant.createReturn(vo);
	}

	
//		MenuVo home = new MenuVo("主页", "/home", "fa-home", null);
//		
//		List<MenuVo> tableList = new ArrayList<MenuVo>();
//		tableList.add(new MenuVo("基本表格", "/table/base", null, null));
//		tableList.add(new MenuVo("排序表格", "/table/sort", null, null));
//		MenuVo table = new MenuVo("表格管理", "/table", "fa-table", tableList);
//		
//		List<MenuVo> chartsList = new ArrayList<MenuVo>();
//		chartsList.add(new MenuVo("柱状图表", "/charts/bar", null, null));
//		MenuVo charts = new MenuVo("图表管理", "/charts", "fa-bar-chart-o", chartsList);
//		
//		List<MenuVo> listMenu = new ArrayList<MenuVo>();
//		listMenu.add(home);
//		listMenu.add(table);
//		listMenu.add(charts);
	public Return<List<MenuVo>> menuNav() {

		List<TMenu> menuList2 = menuDao.findByIsShow(1);
		Iterator<TMenu> menuIter = menuList2.iterator();
		
		List<MenuVo> menuList = new ArrayList<MenuVo>();
		Map<Long, MenuVo> menuMap = new HashMap<Long, MenuVo>();
		
		//Iterator<TMenu> menuIter = menuDao.findAll().iterator();
		while (menuIter.hasNext()) {
			TMenu tMenu = menuIter.next();
			if (tMenu != null) {
				long parentId = tMenu.getParentId();
				if (parentId == 0) {
					MenuVo vo = MenuUtil.change(tMenu);
					if (vo != null) {
						menuMap.put(tMenu.getId(), vo);
						menuList.add(vo);
					}
				} else {
					MenuVo parentVo = menuMap.get(parentId);
					if (parentVo != null) {
						MenuVo vo = MenuUtil.change(tMenu);
						menuMap.put(tMenu.getId(), vo);
						
						List<MenuVo> child = parentVo.getChild();
						if (child == null) {
							child = new ArrayList<MenuVo>();
							parentVo.setChild(child);
						}
						child.add(vo);
					}
				}
			}
		}
		
		return Constant.createReturn(menuList);
	}

}
