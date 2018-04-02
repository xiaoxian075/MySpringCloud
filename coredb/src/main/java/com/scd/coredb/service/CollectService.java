package com.scd.coredb.service;

import com.scd.coredb.dao.CollectDao;
import com.scd.coredb.pojo.db.TCollect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-13.
 */
@Service
@Slf4j
public class CollectService {

    @Autowired
    private CollectDao collectDao;

    public boolean insertOne(TCollect tCollect) {
        try {
            TCollect save = collectDao.save(tCollect);
            if(save == null){
                return false;
            }
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }

    public TCollect findByAccountAndId(long commodityId, String account) {
        Specification<TCollect> spec = new Specification<TCollect>() {
            @Override
            public Predicate toPredicate(Root<TCollect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(criteriaBuilder.equal(root.get(TCollect.COMMODITY_ID).as(Long.class), commodityId));
                predicates.add(criteriaBuilder.equal(root.get(TCollect.ACCOUNT), account));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        TCollect one = collectDao.findOne(spec);
        return one;
    }


    public boolean delectCollect(long commodityId,String account) {
        try {
            collectDao.delectCollect(commodityId,account);
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return false;
    }
}
