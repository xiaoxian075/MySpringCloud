package com.scd.coredb.pojo.util;

import java.util.ArrayList;
import java.util.List;

import com.scd.coredb.pojo.db.TPayRecord;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.joggle.pojo.po.PayResultPo;

public class PayRecordUtil {

	public static PayResultPo changeToResult(TPayRecord tPayRecord) {
		if (tPayRecord == null) {
			return null;
		}

		return new PayResultPo(
				tPayRecord.getTradeOdd(),
				tPayRecord.getOrderOdd(),
				tPayRecord.getAct(),
				tPayRecord.getPayWay(),
//				tPayRecord.getOrderType(),
//				tPayRecord.getPayAccount(),
//				tPayRecord.getReceiveAccount(),
				tPayRecord.getAmount(),
				tPayRecord.getTradeTime()
				);
	}

	public static List<PayResultPo> changeToResult(List<TPayRecord> tList) {
		if (tList == null) {
			return null;
		}
		
		List<PayResultPo> poList = new ArrayList<PayResultPo>();
		for (TPayRecord tPayRecord : tList) {
			PayResultPo po = changeToResult(tPayRecord);
			if (po != null) {
				poList.add(po);
			}
		}
		
		return poList;
	}

	public static List<PayRecordPo> changeToPo(List<TPayRecord> tList) {
		if (tList == null) {
			return null;
		}
		
		List<PayRecordPo> poList = new ArrayList<PayRecordPo>();
		for (TPayRecord tPayRecord : tList) {
			PayRecordPo po = changeToPo(tPayRecord);
			if (po != null) {
				poList.add(po);
			}
		}
		
		return poList;
	}

	private static PayRecordPo changeToPo(TPayRecord tPayRecord) {
		if (tPayRecord == null) {
			return null;
		}
		return new PayRecordPo(
				tPayRecord.getId(),
				tPayRecord.getTradeOdd(),
				tPayRecord.getOrderOdd(),
				tPayRecord.getAct(),
				tPayRecord.getPayWay(),
				tPayRecord.getOrderType(),
				tPayRecord.getPayAccount(),
				tPayRecord.getReceiveAccount(),
				tPayRecord.getAmount(),
				tPayRecord.getTradeTime(),
				tPayRecord.getCreateTime()
				);
	}
}
