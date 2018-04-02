package com.scd.async.third.express.kd100;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Kd100Node {
    /**
     * 时间
     */
    private String time;
    /**
     * 包裹位置信息
     */
    private String context;
}
