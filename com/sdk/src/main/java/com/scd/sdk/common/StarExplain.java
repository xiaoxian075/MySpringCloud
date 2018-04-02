package com.scd.sdk.common;

/**
 * Created by Administrator on 2018-03-06.
 */
public enum StarExplain {


    STAR_ONE(1, "失望"),
    STAR_TWO(2, "不满"),
    STAR_THERR(3, "一般"),
    STAR_FOUR(4, "满意"),
    STAR_FIVE(5, "惊喜");

    private int code;
    private String desc;
    private StarExplain(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
