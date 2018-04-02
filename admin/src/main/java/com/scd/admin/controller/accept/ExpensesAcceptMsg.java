package com.scd.admin.controller.accept;

import com.scd.sdk.util.pojo.BaseAcceptMsg;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ExpensesAcceptMsg extends BaseAcceptMsg {

    private long id;
    private long bullAddrId;
    private String bullAddrName;
    private long commodityAddrId;
    private String commodityAddrName;
    private BigDecimal price;
    private long createTime;
    private long updateTime;

    @Override
    public boolean check() {
        if (id < 0) {
            return false;
        }

        return true;
    }

}
