package com.scd.coredb.feign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.AccountPraiseDao;
import com.scd.coredb.dao.CommunityDao;
import com.scd.coredb.pojo.db.TAccountPraise;
import com.scd.coredb.pojo.db.TCommunity;
import com.scd.coredb.service.CommunityService;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.constant.Type;
import com.scd.joggle.pojo.po.CommunityPo;
import com.scd.sdk.util.RandomUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;

@RestController
@RequestMapping(value = "/community")
public class FCommunity {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommunityDao communityDao;
	
	@Autowired
	private AccountPraiseDao accountPraiseDao;
	

	@RequestMapping(value = "/getPageInfoIncludeState")
    public List<CommunityPo> getPageInfoIncludeState(String account, int type, int page, int size) {
		List<CommunityPo> listPo = null;
    	try {
    		Specification<TCommunity> spec = new Specification<TCommunity>() {
				@Override
				public Predicate toPredicate(Root<TCommunity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
					List<Predicate> predicates = new ArrayList<Predicate>();
					//predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%xian%"));
					predicates.add(criteriaBuilder.equal(root.get(TCommunity.TYPE), type));
		            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
				}
			};
			Order order = new Order(Direction.DESC, TCommunity.ID);
			//Order order = new Order(Direction.DESC, TCommunity.HIT_NUM);
			Pageable pageable = new PageRequest(page, size, new Sort(order));
			Page<TCommunity> pageInfo = communityDao.findAll(spec, pageable);
			if (pageInfo == null) {
				return null;
			}
			List<TCommunity> communityList = pageInfo.getContent();
			if (communityList == null) {
				return null;
			}
			
	    	Map<Long, Integer> praiseMap = getPraiseMap(account);
	    	if (praiseMap == null) {
	    		return null;
	    	}
	    	
	    	listPo = new ArrayList<CommunityPo>();
	    	for (TCommunity tCommunity : communityList) {
	    		boolean isPraise = false;//RandomUtil.randInt(0, 1)==1 ? true : false;
	    		Integer praiseState = praiseMap.get(tCommunity.getId());
	    		if (praiseState != null && praiseState == Type.PRAISE_HIT) {
	    			isPraise = true;
	    		}
	    		listPo.add(tCommunity.createPo(isPraise));
	    	}
	    	
		} catch (Exception e) {
			listPo = null;
			return null;
		}
    	

    	
		return listPo;
    }

    @RequestMapping(value = "/findOne")
    public CommunityPo findOne(long id) {
    	TCommunity tCommunity = communityDao.findOne(id);
    	if (tCommunity == null) {
    		return null;
    	}

    	boolean isPraise = RandomUtil.randInt(0, 1)==1 ? true : false;
    	CommunityPo communityPo = tCommunity.createPo(isPraise);
    	if (communityPo == null) {
    		return null;
    	}
    	
		return communityPo;
    }
    
    @RequestMapping(value = "/insertOne")
    public boolean insertOne(@RequestBody CommunityPo communityPo) {
    	if (communityPo == null) {
    		return false;
    	}
    	try {
	    	TCommunity tCommunity = TCommunity.createNew(communityPo);
	    	communityDao.save(tCommunity);
    	} catch (Exception e) {
			return false;
		}
    	
		return true;
    }
    
    @RequestMapping(value = "/updateOne")
    public boolean updateOne(@RequestBody CommunityPo communityPo) {
    	if (communityPo == null) {
    		return false;
    	}
    	try {
	    	TCommunity tCommunity = TCommunity.createNew(communityPo);
	    	communityDao.save(tCommunity);
    	} catch (Exception e) {
			return false;
		}
    	
		return true;
    }
    
    //@Transactional
    @RequestMapping(value = "/praise")
    public long praise(String account, long id, int type) {
    	long newPraiseNum = -1;
    	try {
			TCommunity tCommunity = communityDao.findOne(id);
			if (tCommunity == null) {
				return -1;
			}
			
			TAccountPraise tAccountPraise = getAccountPraise(account, id);
			
			long praiseNum = tCommunity.getPraiseNum();
			// 验证用户点赞状态
			if (
					((tAccountPraise == null || tAccountPraise.getState() == Type.PRAISE_CANCEL) && type == Type.PRAISE_CANCEL) ||
					((tAccountPraise != null && tAccountPraise.getState() == Type.PRAISE_HIT) && type == Type.PRAISE_HIT) )	{
				return praiseNum;
			}
			
			
			if (type == Type.PRAISE_CANCEL && praiseNum > 0) {
				newPraiseNum = praiseNum - 1;
			} else if (type == Type.PRAISE_HIT) {
				newPraiseNum = praiseNum + 1;
			}
			
			if (newPraiseNum >= 0) {
				long curTime = System.currentTimeMillis();
				if (tAccountPraise != null) {
					tAccountPraise.setState(type);
					tAccountPraise.setUpdateTime(curTime);
				} else {
					tAccountPraise = new TAccountPraise(0L, account, id, type, curTime, curTime);
				}
				saveAccountPraise(tAccountPraise);
				
				tCommunity.setPraiseNum(newPraiseNum);
				tCommunity.setUpdateTime(System.currentTimeMillis());
				communityDao.save(tCommunity);
				
			}
    	} catch (Exception e) {
    		logger.error(e.getMessage());
			return -1;
		}
    	
		return newPraiseNum;
	}
    
    
    
    

    private void saveAccountPraise(TAccountPraise tAccountPraise) {
    	accountPraiseDao.save(tAccountPraise);
	}

	private TAccountPraise getAccountPraise(String account, long communityId) {
		Specification<TAccountPraise> spec = new Specification<TAccountPraise>() {
			@Override
			public Predicate toPredicate(Root<TAccountPraise> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				//predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%xian%"));
				predicates.add(criteriaBuilder.equal(root.get("account").as(String.class), account));
				predicates.add(criteriaBuilder.equal(root.get("communityId").as(Long.class), communityId));
	            
	            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
		return accountPraiseDao.findOne(spec);
	}

	private Map<Long, Integer> getPraiseMap(String account) {
    	List<TAccountPraise> apList = accountPraiseDao.findByAccount(account);
    	if (apList == null) {
    		return null;
    	}
    	
    	Map<Long, Integer> praiseMap = new HashMap<Long, Integer>();
    	for (TAccountPraise t : apList) {
    		praiseMap.put(t.getCommunityId(), t.getState());
    	}
		return praiseMap;
	}
    
    
	
	@Resource
	private CommunityService communityService;

   
	@RequestMapping(value = "/list")
    public Return<PageInfo<CommunityPo>> list(String account, int page, int size) {
		Return<PageInfo<CommunityPo>> ret = null;
		try {
			ret = communityService.list(page, size);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
    }
	
    @RequestMapping(value = "/add")
	public Return<Object> add(String title, int type, String url) {
		Return<Object> ret = null;
		try {
			ret = communityService.add(title, type, url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}


	@RequestMapping(value = "/edit")
	public Return<Object> edit(long id, String title, int type, String url) {
		Return<Object> ret = null;
		try {
			ret = communityService.edit(id, title, type, url);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}

	@RequestMapping(value = "/del")
	public Return<Long> del(long id) {
		Return<Long> ret = null;
		try {
			ret = communityService.del(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
	
	@RequestMapping(value = "/batchDel")
	public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
		Return<List<Long>> ret = null;
		try {
			ret = communityService.batchDel(idList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return ret;
	}
}
