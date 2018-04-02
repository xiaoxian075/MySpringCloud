package com.scd.mweb.service;

import com.scd.joggle.pojo.po.CommentPo;
import com.scd.joggle.pojo.po.CommodityPo;
import com.scd.mweb.constant.Constant;
import com.scd.mweb.feign.FComment;
import com.scd.mweb.feign.FCommodity;
import com.scd.mweb.pojo.vo.CommentVo;
import com.scd.sdk.util.pojo.PageInfo;
import com.scd.sdk.util.pojo.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-03-05.
 */
@Service
public class CommentService {

	@Autowired
    private FComment fComment;
	@Autowired
    private FCommodity fCommodity;

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
}
