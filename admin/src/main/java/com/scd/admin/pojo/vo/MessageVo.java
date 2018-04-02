package com.scd.admin.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageVo {
	private long id;
	private int msgType;
	private String msgTitle;
    private String introduction;
    private String msgContent;
    private int push;
    private int del;
    private long createTime;
}
