package com.scd.coredb.feign;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scd.coredb.constant.Constant;
import com.scd.coredb.dao.CarouselDao;
import com.scd.coredb.pojo.db.TCarousel;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.po.CarouselPo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;

@RestController
@RequestMapping(value = "/carousel")
public class FCarousel {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarouselDao carouselDao;

    @RequestMapping(value = "/findByType")
    public List<CarouselPo> findByType(@RequestParam(value = "type") int type) {
    	List<TCarousel> carouselList = carouselDao.findByType(type);
    	if (carouselList == null) {
    		return null;
    	}
    	
    	List<CarouselPo> listPo = new ArrayList<CarouselPo>();
    	for (TCarousel tCarousel : carouselList) {
    		listPo.add(tCarousel.createPo());
    	}
    	
    	return listPo;
    }
    

    @RequestMapping(value = "/list")
	public Return<PageInfo<CarouselPo>> list(int page, int size) {
		PageInfo<CarouselPo> pPage = null;
		try {
			Pageable pageable = new PageRequest(page, size);
			Page<TCarousel> pageInfo = carouselDao.findAll(pageable);
			
			page = pageInfo.getNumber() + 1;
			long total = pageInfo.getTotalElements();
			List<TCarousel> tList = pageInfo.getContent();
			List<CarouselPo> voList = TCarousel.change(tList);
			
			pPage = new PageInfo<CarouselPo>(page, total, voList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(pPage);
	}
    
    @RequestMapping(value = "/add")
	public Return<Object> add(int type, String url) {
		try {

			long curTime = System.currentTimeMillis();
			TCarousel tCarousel = new TCarousel(0, type, url, curTime, curTime);
			carouselDao.save(tCarousel);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	@RequestMapping(value = "/edit")
	public Return<Object> edit(long id, int type, String url) {
		try {
			TCarousel tCarousel = carouselDao.findOne(id);
			if (tCarousel == null) {
				return Constant.createReturn(ErrorCom.CAROUSEL_NOT_EXIST);
			}
			
			boolean isUpdate = false;
			if (type != tCarousel.getType()) {
				tCarousel.setType(type);
				isUpdate = true;
			}
			if (!url.equals(tCarousel.getUrl())) {
				tCarousel.setUrl(url);
				isUpdate = true;
			}
			
			if (!isUpdate) {
				return Constant.createReturn(ErrorCom.COM_NO_UPDATE);
			}
			
			tCarousel.setUpdateTime(System.currentTimeMillis());
			carouselDao.save(tCarousel);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn();
	}


	@RequestMapping(value = "/del")
	public Return<Long> del(long id) {
		try {
			carouselDao.delete(id);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		return Constant.createReturn(id);
	}
	
	@Transactional
	@RequestMapping(value = "/batchDel")
	public Return<List<Long>> batchDel(@RequestBody List<Long> idList) {
		try {
			for (long id : idList) {
				carouselDao.delete(id);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.createReturn(ErrorCom.ERROR);
		}
		
		return Constant.createReturn(idList);
	}

	
}
