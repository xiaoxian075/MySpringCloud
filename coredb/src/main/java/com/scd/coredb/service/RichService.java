package com.scd.coredb.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.RichDao;
import com.scd.coredb.pojo.db.TRich;
import com.scd.coredb.pojo.util.RichUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.RichBo;
import com.scd.joggle.pojo.bo.RichLinkBo;
import com.scd.sdk.util.DesUtil;
import com.scd.sdk.util.GsonUtil;
import com.scd.sdk.util.pojo.Return;

@Service
public class RichService {
	
	@Autowired
	private RichDao richDao;

	public Return<RichBo> selectOne(long type, long foreignId) {
		RichBo bo = null;
		
		TRich tRich = _selectOne(foreignId, type);
		if (tRich == null) {
			bo = new RichBo(type, foreignId, 0, "");
		} else {
			bo = RichUtil.change(tRich);
			if (bo == null) {
				return Constant.createReturn(ErrorCom.CHANGE_ERROR);
			}
		}
		
		return Constant.createReturn(bo);
	}

	public Return<RichBo> submit(RichBo richBo) {
		String data = DesUtil.base64EncodeFromStr(richBo.getData());
		if (data == null) {
			return Constant.createReturn(ErrorCom.CHANGE_ERROR);
		}
		
		long curTime = System.currentTimeMillis();
		long foreignId = richBo.getForeignId();
		long id = richBo.getId();
		long type = richBo.getType();
		
		RichLinkBo bo = new RichLinkBo(type, id);
		String link = GsonUtil.toString(bo);
		if (link == null) {
			return Constant.createReturn(ErrorCom.SAVE_FAIL);
		}

		TRich tRich = _selectOne(foreignId, type);
		if (tRich == null) {
			tRich = new TRich(0L, type, foreignId, data, link, curTime, curTime);
		} else {
			tRich.setData(data);
			tRich.setLink(link);
			tRich.setUpdateTime(curTime);
		}
		
		TRich newTRich = richDao.save(tRich);
		if (newTRich == null) {
			return Constant.createReturn(ErrorCom.SAVE_FAIL);
		}
		
		RichBo newBo = RichUtil.change(newTRich);
		return Constant.createReturn(newBo);
	}
	
	private TRich _selectOne(long foreignId, long type) {
		Specification<TRich> spec = new Specification<TRich>() {
    		@Override
    		public Predicate toPredicate(Root<TRich> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			predicates.add(criteriaBuilder.equal(root.get(TRich.FOREIGN_ID).as(Long.class), foreignId));
    			predicates.add(criteriaBuilder.equal(root.get(TRich.TYPE).as(Long.class), type));
    			
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
		return richDao.findOne(spec);
	}

}
