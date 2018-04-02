package com.scd.joggle.pojo.param;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestPayParam {
    String account;
    String odd;
    int payWay;		 // 1：余额  2：sdk
    String payPassWord;
}
