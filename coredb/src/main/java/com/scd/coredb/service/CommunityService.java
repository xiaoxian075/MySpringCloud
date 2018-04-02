package com.scd.coredb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.CommunityDao;
import com.scd.coredb.pojo.db.TCommunity;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@Service
public class CommunityService {
	
	@Autowired
	private CommunityDao communityDao;

    public Return<PageInfo<CommunityPo>> list(int page, int size) {

		Pageable pageable = new PageRequest(page, size);
		Page<TCommunity> pageInfo = communityDao.findAll(pageable);
		
		page = pageInfo.getNumber() + 1;
		long total = pageInfo.getTotalElements();
		List<TCommunity> tList = pageInfo.getContent();
		List<CommunityPo> voList = TCommunity.change(tList);
		
		PageInfo<CommunityPo> pPage = new PageInfo<CommunityPo>(page, total, voList);

		return Constant.createReturn(pPage);
    }

	public Return<Object> add(String title, int type, String url) {

		long curTime = System.currentTimeMillis();
		TCommunity tCommunity = new TCommunity(0, title, type, url, 0, 0, curTime, curTime);
		communityDao.save(tCommunity);

		return Constant.createReturn();
	}


	public Return<Object> edit(long id, String title, int type, String url) {

		TCommunity tCommunity = communityDao.findOne(id);
		if (tCommunity == null) {
			return Constant.createReturn(ErrorCom.COMMUNITY_NOT_EXIST);
		}
		
		boolean isUpdate = false;
		if (!title.equals(tCommunity.getTitle())) {
			tCommunity.setTitle(title);
			isUpdate = true;
		}
		if (type != tCommunity.getType()) {
			tCommunity.setType(type);
			isUpdate = true;
		}
		if (!url.equals(tCommunity.getUrl())) {
			tCommunity.setUrl(url);
			isUpdate = true;
		}
		
		if (!isUpdate) {
			return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
		}
		
		tCommunity.setUpdateTime(System.currentTimeMillis());
		communityDao.save(tCommunity);

		return Constant.createReturn();
	}

	public Return<Long> del(long id) {
	
		communityDao.delete(id);

		return Constant.createReturn(id);
	}
	
	@Transactional
	public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {

		for (long id : idList) {
			communityDao.delete(id);
		}
		
		return Constant.createReturn(idList);
	}
}
