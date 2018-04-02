package com.scd.joggle.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RichBo {

	private long type;
	private long foreignId;
	private long id;
	private String data;
}
