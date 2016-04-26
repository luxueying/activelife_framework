package com.activelife.common.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends  StringUtils{

	public static String getValue(double source) {
		String result = "";
		if (source > 0) {
			result = String.valueOf(source);
		}

		return result;
	}
}
