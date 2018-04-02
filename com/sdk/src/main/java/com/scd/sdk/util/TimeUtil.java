package com.scd.sdk.util;

import java.text.SimpleDateFormat;

public class TimeUtil {

	public static String changeToStr(long timestamp, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(timestamp);
	}

}
