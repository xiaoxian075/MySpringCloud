package com.scd.app.controller.accept;

import com.scd.app.pojo.vo.CommentAddVo;
import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2018-03-05.
 */
@Getter
@Setter
@AllArgsConstructor
public class CommentAddAcceptMsg extends BaseAcceptMsg {


    private List<CommentAddVo> contentList;
    private String odd;

    @Override
    public boolean check() {
       if(contentList.size() == 0){
           return false;
       }
       if(odd == null){
           return false;
       }
        return true;
    }
}
