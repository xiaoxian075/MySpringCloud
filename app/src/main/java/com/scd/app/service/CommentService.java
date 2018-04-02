package com.scd.app.service;

import com.scd.app.constant.Constant;
import com.scd.app.controller.accept.CommentAddAcceptMsg;
import com.scd.app.feign.FAccount;
import com.scd.app.feign.FComment;
import com.scd.app.feign.FCommodity;
import com.scd.app.feign.FOrder;
import com.scd.app.mgr.FileMgr;
import com.scd.app.pojo.util.CommentUtil;
import com.scd.app.pojo.vo.CommentAddVo;
import com.scd.app.pojo.vo.CommentVo;
import com.scd.joggle.constant.ErrorCom;
import com.scd.joggle.pojo.bo.AccountBo;
import com.scd.joggle.pojo.po.CommentAddPo;
import com.scd.joggle.pojo.po.CommentPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.sdk.util.StarExplainUtil;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-05.
 */
@Service
@Slf4j
public class CommentService {

	@Autowired
    private FComment fComment;
	@Autowired
    private FCommodity fCommodity;
	@Autowired
    private FOrder fOrder;
	@Autowired
    private FAccount fAccount;

    public Return<PageInfo<CommentVo>> getCommentList(int page, int size, long goodsId) {

        Return<CommodityPo> one = fCommodity.getOne(goodsId);
        if(Return.isErr(one)){
            return Constant.createReturn(one.getCode(),one.getDesc());
        }

        PageInfo<CommentVo> kdPage = null;
        Return<PageInfo<CommentPo>>  poList = fComment.getAppCommentList(page,size,goodsId);
        List<CommentVo> voList = new ArrayList<>();

        if(!Return.isErr(poList)){
            List<CommentPo> po = poList.getData().getList();
            if(po.size() != 0){
                for(CommentPo commentPo : po){
                    ArrayList<String> list = new ArrayList<String>();
                    String pictureUrl = commentPo.getPictureUrl();
                    if(pictureUrl != null){
                        String[] split = pictureUrl.split(",");
                        for(String str : split){
                            list.add(str);
                        }
                    }
                    CommentVo commentVo = new CommentVo(
                            commentPo.getId(),
                            commentPo.getIsAnonymous(),
                            commentPo.getAccountId(),
                            commentPo.getAccountHeadUrl(),
                            commentPo.getNickName(),
                            commentPo.getContent(),
                            commentPo.getCommentTime(),
                            list
                    );
                    voList.add(commentVo);
                }
            }
            kdPage= new PageInfo<>(poList.getData().getPage(),poList.getData().getTotal(),voList);
        }
        return Constant.createReturn(kdPage);
    }

    public Return<Object> addComment(CommentAddAcceptMsg acceptMsg) {
        try {
            List<CommentAddVo> contentList = acceptMsg.getContentList();
            List<CommentAddVo> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for(CommentAddVo commentAddVo : contentList){
                commentAddVo.setGoodsDescribe(StarExplainUtil.getExplain(commentAddVo.getDescribeStar()));
                commentAddVo.setLogistics(StarExplainUtil.getExplain(commentAddVo.getLogisticsStar()));
                commentAddVo.setService(StarExplainUtil.getExplain(commentAddVo.getServiceStar()));
                String pictureUrl = commentAddVo.getPictureUrl();
                if(pictureUrl != null){
                    String[] split = pictureUrl.split(",");
                    for(String str : split){
                        String url = FileMgr.getInstance().saveSowingStr(str);
                        System.out.println(url);
                        sb.append(url+",");
                    }
                    commentAddVo.setPictureUrl(sb.substring(0,sb.toString().length()-1));
                }
                AccountBo infoByAccount = fAccount.getInfoByAccount(commentAddVo.getAccount());
                commentAddVo.setAccount(infoByAccount.getAccount());
                commentAddVo.setNickName(infoByAccount.getNickName());
                commentAddVo.setHeadPicUrl(infoByAccount.getHeadUrl());
                list.add(commentAddVo);
            }

            List<CommentAddPo> poList = CommentUtil.changePo(list);
            Return<Object> ret = fComment.addComment(poList);
            if (Return.isErr(ret)) {
                return Constant.createReturn(ret.getCode(), ret.getDesc());
            }

            for(int i = 0;i<contentList.size();i++){
                fOrder.dealEvaluatedByOdd(contentList.get(i).getOrderId());
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return Constant.createReturn(ErrorCom.ERROR);
        }
        return Constant.createReturn();
    }
}
