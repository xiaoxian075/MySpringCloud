package com.scd.joggle.pojo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpensesParam {
    private long id;
    private long bullAddrId;
    private String bullAddrName;
    private long commodityAddrId;
    private String commodityAddrName;
    private BigDecimal price;
    private long createTime;
    private long updateTime;
}
