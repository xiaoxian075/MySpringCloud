package com.scd.coredb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scd.coredb.dao.PayAsynNoticeDao;
import com.scd.coredb.pojo.db.TPayAsynNotice;
import com.scd.joggle.pojo.bo.YftAsynNoticeBo;

@Service
public class PayAsynNoticeService {
	
	@Autowired
	private PayAsynNoticeDao payAsynNoticeDao;

	public boolean save(YftAsynNoticeBo info) {
		TPayAsynNotice tPayAsynNotice = payAsynNoticeDao.findByTradeNo(info.getTradeNo());
		if (tPayAsynNotice == null) {
			tPayAsynNotice = new TPayAsynNotice(
					0L,
					info.getTradeNo(),
					info.getNoticeType(),
					info.getState(),
					info.getPayYunId(),
					info.getOrderId(),
					info.getRealAmount(),
					info.getTradeTime(),
					System.currentTimeMillis()
					);
			
			TPayAsynNotice newTPayAsynNotice = payAsynNoticeDao.save(tPayAsynNotice);
			if (newTPayAsynNotice == null) {
				return false;
			}
		}
		return true;
	}
	
	public YftAsynNoticeBo get(String tradeNo) {
		TPayAsynNotice tPayAsynNotice = payAsynNoticeDao.findByTradeNo(tradeNo);
		if (tPayAsynNotice == null) {
			return null;
		}
		
		return new YftAsynNoticeBo(
				tPayAsynNotice.getTradeNo(),
				tPayAsynNotice.getNoticeType(),
				tPayAsynNotice.getState(),
				tPayAsynNotice.getPayYunId(),
				tPayAsynNotice.getOrderId(),
				tPayAsynNotice.getRealAmount(),
				tPayAsynNotice.getTradeTime()
				);
				
	}

}
