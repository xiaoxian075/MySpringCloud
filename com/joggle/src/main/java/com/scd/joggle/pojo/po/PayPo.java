package com.scd.joggle.pojo.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PayPo {
    //private long orderId;
    //private String account;
    private String odd;
    private BigDecimal amount;
    private int type;// 1：单笔  2：多笔合购
    private List<String> childOdd;
}
