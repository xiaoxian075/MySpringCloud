package com.scd.admin.pojo.util;

import com.scd.admin.dao.pojo.TMenu;
import com.scd.admin.pojo.vo.MenuVo;

public class MenuUtil {

	public static MenuVo change(TMenu tMenu) {
		return new MenuVo(tMenu.getTitle(), tMenu.getPath(), tMenu.getIcon(), null);
	}
}
