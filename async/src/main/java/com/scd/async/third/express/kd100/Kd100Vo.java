package com.scd.async.third.express.kd100;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kd100Vo {
    /**
     *   快递单当前的状态 ：　
     *   0：在途，即货物处于运输过程中
     *   1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息
     *   2：疑难，货物寄送过程出了问题
     *   3：签收，收件人已签收
     *   4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收
     *   5：派件，即快递正在进行同城派件
     *   6：退回，货物正处于退回发件人的途中
     */
    private int state;
    /**
     * 返回结果说明
     */
    private String message;
    /**
     * 返回的data数组
     */
    private List<Kd100Node> kuaidiDataList;
}
