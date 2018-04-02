package com.scd.coredb.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.VersionDao;
import com.scd.coredb.pojo.db.TVersion;
import com.scd.coredb.pojo.util.VersionUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.VersionPo;
import com.scd.sdk.util.pojo.Return;

@Service
public class VersionService {
	
	@Autowired
	private VersionDao versionDao;

	public Return<VersionPo> getVersion(int type) {
		TVersion tVersion = versionDao.findByType(type);
		if (tVersion == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		VersionPo po = VersionUtil.change(tVersion);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(po);
	}

	public Return<List<VersionPo>> getAll() {
		Iterator<TVersion> iterable = versionDao.findAll().iterator();
		List<TVersion> tList = Lists.newArrayList(iterable);
		List<VersionPo> poList = VersionUtil.change(tList);
		if (poList == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}

		return Constant.createReturn(poList);
	}

	public Return<VersionPo> edit(long id, String version, int state) {
		TVersion tVersion = versionDao.findOne(id);
		if (tVersion == null) {
			return Constant.createReturn(ErrorCom.NOT_EXIST);
		}
		
		
		boolean isUpdate = false;
		if (version != null && !version.equals(tVersion.getVersion())) {
			tVersion.setVersion(version);
			isUpdate = true;
		}
		
		if (state > 0 && state != tVersion.getState()) {
			tVersion.setState(state);
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		TVersion newTVersion = versionDao.save(tVersion);
		if (newTVersion == null) {
			return Constant.createReturn(ErrorCom.SAVE_ERROR);
		}
		
		VersionPo po = VersionUtil.change(newTVersion);
		if (po == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		return Constant.createReturn(po);
	}

}
