/**
 * Copyright &copy; 2016-2018 <a href="http://gitlab.ipubtrans.cn">ipubase</a> All rights reserved.
 */
package com.zt.queryplatform.base;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类
 */
public class DateUtils {
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	/**
	 * 获取某天的前N天或后N天
	 */
	public static Date getDateToN(Date date, int n) {
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(Calendar.DATE,n);
		 date=calendar.getTime();
		 return date;
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

}
