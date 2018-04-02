package com.scd.mweb.controller.accept;

import com.scd.sdk.util.pojo.H5BasePageAcceptMsg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2018-03-05.
 */
@Getter
@Setter
@AllArgsConstructor
public class CommentAcceptMsg extends H5BasePageAcceptMsg {

    private long goodsId;

    @Override
    public boolean check() {
        if (goodsId < 0) {
            return false;
        }
        page = getPage(page);
        size = getSize(size);
        return true;
    }
}
