package com.scd.app.third.kd100;

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
     * 包裹位置信息
     */
    private String context;
    private String location;
    /**
     * 时间
     */
    private String time;
}
