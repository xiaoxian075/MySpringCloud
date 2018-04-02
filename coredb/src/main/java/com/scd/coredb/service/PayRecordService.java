package com.scd.coredb.service;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.PayRecordDao;
import com.scd.coredb.pojo.db.TPayRecord;
import com.scd.coredb.pojo.util.PayRecordUtil;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.param.PayRecordSelectParam;
import com.scd.joggle.pojo.po.PayRecordPo;
import com.scd.joggle.pojo.po.PayResultPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PayRecordService {

	@Autowired
	private PayRecordDao payRecordDao;

	
	public TPayRecord getByTradeOdd(String tradeOdd) {
		TPayRecord tPayRecord = payRecordDao.findByTradeOdd(tradeOdd);
		if (tPayRecord == null) {
			return null;
		}
		return tPayRecord;
	}

	public TPayRecord save(TPayRecord tPayRecord) {
		if (tPayRecord == null) {
			return null;
		}

		TPayRecord newTPayRecord = payRecordDao.save(tPayRecord);
		if (newTPayRecord == null) {
			return null;
		}
		return newTPayRecord;
	}

	public TPayRecord getByOrderOdd(String orderOdd) {
		TPayRecord tPayRecord = payRecordDao.findByOrderOdd(orderOdd);
		if (tPayRecord == null) {
			return null;
		}
		return tPayRecord;
	}

	public Return<List<PayResultPo>> getList(int page, int size, String account) {

		Specification<TPayRecord> spec = new Specification<TPayRecord>() {
	  		@Override
	  		public Predicate toPredicate(Root<TPayRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
		
	  			List<Predicate> predicates = new ArrayList<Predicate>();
	  			if (account != null) {
	  				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.PAY_ACCOUNT).as(String.class), account));
	  			}
	  		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
	  		}
		};
		
      Order order = new Order(Direction.DESC, TPayRecord.CREATE_TIME);
      Pageable pageable = new PageRequest(page, size, new Sort(order));
      Page<TPayRecord> pageInfo = payRecordDao.findAll(spec, pageable);

      List<PayResultPo> poList = PayRecordUtil.changeToResult(pageInfo.getContent());
      if (poList == null) {
    	  return Constant.createReturn(ErrorCom.CHANGE_ERROR);
      }

      return Constant.createReturn(poList);
	}
	
    public Return<PageInfo<PayRecordPo>> list(PayRecordSelectParam param) {

        String tradeOdd = param.getTradeOdd();
        String orderOdd = param.getOrderOdd();
        String payAccount = param.getPayAccount();
        String receiveAccount = param.getReceiveAccount();
        int payWay = param.getPayWay();
        int act = param.getAct();
    	Specification<TPayRecord> spec = new Specification<TPayRecord>() {
    		@Override
    		public Predicate toPredicate(Root<TPayRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
	
    			List<Predicate> predicates = new ArrayList<Predicate>();
    			if (tradeOdd != null) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.TRADE_ODD).as(String.class), tradeOdd));
    			}
    			if (orderOdd != null) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.ORDER_ODD).as(String.class), orderOdd));
    			}
    			if (payAccount != null) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.PAY_ACCOUNT).as(String.class), payAccount));
    			}
    			if (receiveAccount != null) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.RECEIVE_ACCOUNT).as(String.class), receiveAccount));
    			}
    			if (payWay > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.PAY_WAY).as(Integer.class), payWay));
    			}
    			if (act > 0) {
    				predicates.add(criteriaBuilder.equal(root.get(TPayRecord.ACT).as(Integer.class), act));
    			}
    		    return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    		}
		};
		
        Order order = new Order(Direction.DESC, TPayRecord.CREATE_TIME);
        Pageable pageable = new PageRequest(param.getPage(), param.getSize(), new Sort(order));
        Page<TPayRecord> pageInfo = payRecordDao.findAll(spec, pageable);

        int page = pageInfo.getNumber() + 1;
        long total = pageInfo.getTotalElements();
        List<PayRecordPo> poList = PayRecordUtil.changeToPo(pageInfo.getContent());
        if (poList == null) {
      	  return Constant.createReturn(ErrorCom.CHANGE_ERROR);
        }

        PageInfo<PayRecordPo> kdPage = new PageInfo<PayRecordPo>(page, total, poList);

        return Constant.createReturn(kdPage);
    }
}
