package com.volshell.util;

import com.volshell.parser.constant.*;

/**
 * @author volshell
 * @version 1.0
 * @date 2016年4月25日
 * 
 */
public class URLParser {
	/**
	 * 实现通过url判断是否为指定的类型，并返回相应的code；否则返回ERROR
	 * 
	 * @param url
	 * @param type
	 *            目前只支持两种类型:category/viewnews
	 * @return
	 */
	public static String getTypeCode(String url, String type) {
		int lastDelim = url.lastIndexOf("/");
		String suffix = url.substring(lastDelim + 1, url.length());
		// System.out.println(suffix);
		if (suffix.startsWith(type.trim())) {
			String code = suffix.substring(suffix.lastIndexOf("-") + 1, suffix.length());
			return code;
		}
		return Constants.ERROR;
	}
}
