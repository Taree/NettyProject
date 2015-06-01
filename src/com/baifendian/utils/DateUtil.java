/* ===========================================================================
 * $                                                                         $
 * $ (C) Copyright http://www.uuwatch.com 2007-2008  All rights reserved.    $
 * $                                                                         $
 * ===========================================================================
 * $Revision:: 71051                                                         $ 
 * $LastChangedBy:: cyg                                                      $ 
 * $LastChangedDate:: 2014-08-13 15:20:21 +0800 #$                           $
 * ===========================================================================
 */

package com.baifendian.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期时间辅助程序
 * 
 * @author caviler
 * @author ice
 * @author edwin
 */
public final class DateUtil extends DateParser {
	private static final String[] DAY_OF_WEEK_STRING = { "周日", "周一", "周二",
			"周三", "周四", "周五", "周六" };

	/* 1h */
	public static final long HOUR = 60 * 60 * 1000;

	public static final String HOURMINUTES_TIME_FORMAT = "HH:mm"; // 24小时时间格式

	public static final String JS_DATE_FORMAT = "yyyy/M/d"; // JavaScript

	public static final String JS_DATETIME_FORMAT = "yyyy/M/d HH:mm:ss"; // JavaScript

	public static final String JS_TIME_FORMAT = "HH:mm:ss"; // JavaScript

	public static final int MILLISECONDS_OF_DAY = 24 * 60 * 60 * 1000; // 一天的微妙数

	public static final int MILLISECONDS_OF_HOUR = 60 * 60 * 1000; // 一小时的微妙数


	/* 1m */
	public static final long MINUTE = 60 * 1000;

	/* 1s */
	public static final long SECOND = 1000L;

	/**
	 * 日期增加天数，可以是正负值
	 * 
	 * @param date
	 *            Date
	 * @param nDays
	 *            天数;可以是正负值
	 * @return Date
	 */
	public static Date addDays(final Date date, final int nDays) {
		if (null == date) {
			return date;
		}
		return new Date(date.getTime() + (long) nDays * MILLISECONDS_OF_DAY);
	}

	/**
	 * 增加小时数，可以是正负值
	 * 
	 * @param date
	 *            Date
	 * @param nHours
	 *            小时
	 * @return Date
	 */
	public static Date addHours(final Date date, final int nHours) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.HOUR, nHours);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 增加毫秒，可以为正负值
	 * 
	 * @param date
	 *            Date
	 * @param milliseconds
	 *            毫秒数,可以是正负值
	 * @return Date
	 */
	public static Date addMilliseconds(final Date date, final int milliseconds) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MILLISECOND, milliseconds);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 增加分钟，可以为正负值
	 * 
	 * @param date
	 *            Date
	 * @param minuts
	 *            分钟数可以为正负值
	 * @return Date
	 */
	public static Date addMinutes(final Date date, final int minuts) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MINUTE, minuts);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 增加月份，可以是正负值
	 * 
	 * @param date
	 *            Date
	 * @param nMonths
	 *            月份数;可以是正负值
	 * @return Date
	 */
	public static Date addMonths(final Date date, final int nMonths) {
		if (null == date) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, nMonths);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 增加秒，可以为正负值
	 * 
	 * @param date
	 *            Date
	 * @param seconds
	 *            毫秒数可以为正负值
	 * 
	 * @return Date
	 */
	public static Date addSeconds(final Date date, final int seconds) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, seconds);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 增加年，可以是正负值
	 * 
	 * @param date
	 *            Date
	 * @param nYears
	 *            年数;可以是正负值
	 * @return Date
	 */
	public static Date addYears(final Date date, final int nYears) {
		if (null == date) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, nYears);
		final Date end = c.getTime();
		return end;
	}

	/**
	 * 判断指定的时间 date 是否在某个时间范围(d1-d2)内; 时间可以为 null, date 为 null 则表示当前时间
	 * 
	 * @param date
	 *            需要判断的时间
	 * @param d1
	 *            开始时间
	 * @param d2
	 *            结束时间
	 * @return true是在此范围,false不在此范围
	 */
	public static boolean checkDateInRange(final Date date, final Date d1,
			final Date d2) {
		if ((null == d1) && (null == d2)) {
			return true; // 开始时间结束时间为空
		}

		final Date d = (null == date) ? new Date() : date;

		if ((null != d2) && d2.before(d)) // 只有结束时间
		{
			return false;
		}

		if ((null != d1) && d1.after(d)) // 只有开始时间
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断指定的时间 date 是否在某个时间范围(d1-d2)内; 时间可以为 null, date 为 null 则表示当前时间
	 * 
	 * @param date
	 *            需要判断的时间
	 * @param d1
	 *            开始时间
	 * @param d2
	 *            结束时间
	 * @param contentEnd
	 *            是否包含结束时间
	 * @return true是在此范围,false不在此范围
	 */
	public static boolean checkDateInRange(final Date date, final Date d1,
			final Date d2, final boolean contentEnd) {
		if ((null == d1) && (null == d2)) {
			return true; // 开始时间结束时间为空
		}

		final Date d = (null == date) ? new Date() : date;

		if (!contentEnd) {
			// 不包含结束时间
			if ((null != d2) && d2.getTime() == d.getTime()) // 只有结束时间,且结束时间与date相同
			{
				return false;
			}
		}

		if ((null != d2) && d2.before(d)) // 只有结束时间
		{
			return false;
		}

		if ((null != d2) && d2.before(d)) // 只有结束时间
		{
			return false;
		}

		if ((null != d1) && d1.after(d)) // 只有开始时间
		{
			return false;
		}

		return true;
	}

	/**
	 * 判断两个日期范围是否重叠; 日期可以为 null
	 * 
	 * @param d11
	 *            Date
	 * @param d12
	 *            Date
	 * @param d21
	 *            Date
	 * @param d22
	 *            Date
	 * @return 如果重叠返回 true
	 */
	public static boolean checkDateOverlapped(final Date d11, final Date d12,
			final Date d21, final Date d22) {
		if ((null == d11) && (null == d12) && (null == d21) && (null == d22)) {
			return true; // 全部日期为空
		}

		if ((null == d11) && (null == d12)) {
			return true; // 第一日期范围为空
		}

		if ((null == d21) && (null == d22)) {
			return true; // 第二日期范围为空
		}

		if ((null != d11) && (null != d22) && d22.before(d11)) {
			return false; // 第二日期范围在第一日期范围前面
		}

		if ((null != d12) && (null != d21) && d21.after(d12)) {
			return false; // 第二日期范围在第一日期范围后面
		}

		if ((null != d11) && (null == d12))// 第一日期范围只指定了开始时间
		{
			return ((null != d22) && d22.before(d11)) ? false : true; // 只有在第二日期范围的结束时间比第一日期范围的开始时间早的情况下返回假
		}

		if ((null == d11) && (null != d12))// 第一日期范围只指定了结束时间
		{
			return ((null != d21) && d21.after(d12)) ? false : true; // 只有在第二日期范围的开始时间比第一日期范围的结束时间晚的情况下返回假
		}

		// 指定了四个时间
		return ((null != d22 && d22.before(d11)) || ((null != d21) && d21
				.after(d12))) ? false : true; //
	}

	/**
	 * 判断指定的时间 date 是否在某个时间范围(d1-d2)内; 时间可以为 null, date 为 null 则表示当前时间
	 * 
	 * @param date
	 *            需要判断的时间
	 * @param d1
	 *            开始时间
	 * @param d2
	 *            结束时间
	 * @param contentEnd
	 *            是否包含结束时间
	 * @return true是在此范围,false不在此范围
	 */
	public static boolean checkIndexDateInRange(final Date d11, final Date d12,
			final Date d21, final Date d22) {
		if (checkDateOverlapped(d11, d12, d21, d22)) {
			if (null != d22) {
				// 索引的日期范围（目前的结束日期不能包含）
				if (null != d11 && d11.getTime() >= d22.getTime()
						&& (null != d21 && d21.getTime() != d22.getTime())) {
					return false;
				}
			}

			return true;
		}
		return false;

	}

	/**
	 * 时间转字符串(JavaScript 格式)
	 * 
	 * @param date
	 *            Date
	 * @return 日期字符串(JavaScript 格式)
	 */
	public static String dateTimeToJSString(final Date date) {
		return dateTimeToString(date, JS_DATETIME_FORMAT);
	}

	/**
	 * 日期时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @return 日期时间字符串(DEFAULT_DATETIME_FORMAT -- yyyy-MM-dd HH:mm:ss)
	 */
	public static String dateTimeToString(final Date date) {
		return dateTimeToString(date, DEFAULT_DATETIME_FORMAT);
	}

	/**
	 * 日期时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            日期时间字符串格式
	 * @return 日期时间字符串
	 */
	public static String dateTimeToString(final Date date, final String format) {
		if (date != null) {
			final SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		}

		return null;
	}

	/**
	 * 日期时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            日期时间字符串格式
	 * @param value
	 *            当date为空时的默认值
	 * @return 日期时间字符串
	 */
	public static String dateTimeToString(final Date date, final String format,
			final String value) {
		if (date != null) {
			final SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(date);
		}

		return value;
	}

	/**
	 * 日期转字符串(JavaScript 格式)
	 * 
	 * @param date
	 *            Date
	 * @return 日期字符串(JavaScript 格式)
	 */
	public static String dateToJSString(final Date date) {
		return dateTimeToString(date, JS_DATE_FORMAT);
	}

	/**
	 * 日期转字符串
	 * 
	 * @param date
	 *            Date
	 * @return 日期字符串(DEFAULT_DATE_FORMAT)
	 */
	public static String dateToString(final Date date) {
		return dateTimeToString(date, DEFAULT_DATE_FORMAT);
	}

	/**
	 * @param timeQuantum
	 *            两个时间戳相减的数字（60进位的）
	 * @return 格式 HH:mm:ss
	 */
	public static String db2TimeQuantumToString(final int timeQuantum) {
		String result = "";
		int temp;
		// 秒级的处理
		if (timeQuantum > 0) {
			temp = timeQuantum % 100;
			if (temp == 0) {
				result = "00";
			} else if (temp < 10) {
				result = "0" + temp;
			} else if (temp < 60 && temp >= 10) {
				result = Integer.toString(temp);
			}
		} else {
			result = "00";
		}

		// 达到分钟的处理
		if (timeQuantum >= 100) {
			temp = timeQuantum / 100;
			temp = temp % 100;
			if (temp == 0) {
				result = "00:" + result;
			} else if (temp < 10) {
				result = "0" + temp + ":" + result;
			} else if (temp < 60 && temp >= 10) {
				result = temp + ":" + result;
			}
		} else {
			result = "00" + ":" + result;
		}
		// 达到小时的处理
		if (timeQuantum >= 10000) {
			temp = timeQuantum / 10000;
			temp = temp % 100;
			if (temp == 0) {
				result = "00:" + result;
			} else if (temp < 10) {
				result = "0" + temp + ":" + result;
			} else if (temp < 24 && temp >= 10) {
				result = temp + ":" + result;
			}
		} else {
			result = "00" + ":" + result;
		}
		return result;
	}

	// 日期格式

	public static Date getBaseDate() {
		return makeDateTime(1970, 1, 1, 8, 0, 0, 0); // == new Date(0)
	}

	public static Date getBaseTime() {
		return makeDateTime(1970, 1, 1, 0, 0, 0, 0);
	}

	/**
	 * 获得百进制的时间，如：12:23:45->122345
	 * 
	 * @param oDate
	 *            时间
	 * @return 转为百进位后的时间
	 */
	public static int getCentesimalDate(final Date oDate) {
		if (null == oDate) {
			return -1;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		return cal.get(Calendar.HOUR_OF_DAY) * 10000 + cal.get(Calendar.MINUTE)
				* 100 + cal.get(Calendar.SECOND);
	}

	/**
	 * 取得当前时间的字符串形式
	 * 
	 * @return 当前时间字符串
	 */
	public static String getCurrentTimeString() {
		return dateTimeToString(new Date());
	}

	/**
	 * 获取两个日期间的所有日期,包含首尾日期
	 * 
	 * @param start
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @return
	 */
	public static List<Date> getDateList(final Date date1, final Date date2) {
		final List<Date> dates = new ArrayList<Date>();

		if (date1 == null || date2 == null) {
			return dates;
		}

		Date date = date1;
		while (!date.after(date2)) {
			dates.add(date);
			date = addDays(date, 1);
		}

		return dates;
	}

	/**
	 * 求两个日期差
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 两个日期相差天数
	 */
	public static long getDateMargin(final Date beginDate, final Date endDate) {
		long margin = 0;

		margin = endDate.getTime() - beginDate.getTime();
		margin = margin / MILLISECONDS_OF_DAY;

		return margin;
	}

	/**
	 * 修改日期时间对象的时间部分为 00:00:00.0000
	 * 
	 * @param oDate
	 *            日期
	 * @return 修改时间部分后的日期
	 */
	public static Date getDateOnly(final Date oDate) {
		if (null == oDate) {
			return oDate;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 取得符合日期范围的日期数组下标集合
	 * 
	 * @param date1
	 * @param date2
	 * @param dates
	 * @return
	 */
	public static List<Integer> getDateRangeIndexs(final Date date1,
			final Date date2, final Date[][] dates) {
		if (null == dates) {
			return new ArrayList<Integer>();
		}

		final List<Integer> datesIndex = new ArrayList<Integer>();
		final int n = dates.length;
		for (int i = 0; i < n; i++) {
			if (DateUtil.checkIndexDateInRange(date1, date2, dates[i][0],
					dates[i][1])) {
				datesIndex.add(i);
			}
		}

		return datesIndex;
	}

	/**
	 * 获取两个日期间的所有日期,包含首尾日期
	 * 
	 * @param start
	 *            起始日期
	 * @param end
	 *            结束日期
	 * @return
	 */
	public static List<Date> getDateTimeList(final Date time1, final Date time2) {
		final List<Date> times = new ArrayList<Date>();

		if (time1 == null || time2 == null) {
			return times;
		}

		Date date = time1;
		while (!date.after(time2)) {
			times.add(date);
			date = addMinutes(date, 1);
		}

		return times;

	}

	/**
	 * 返回月中第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 返回周几
	 * 
	 * @param date
	 *            Date
	 * @return 返回周几(0-6)[0代表周日；1代表周一]
	 * 
	 */
	public static int getDayOfWeek(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
	}

	/**
	 * 返回周几字符串
	 * 
	 * @param date
	 *            Date
	 * @return 返回周几(0-6)
	 */
	public static String getDayOfWeekString(final Date date) {
		return DAY_OF_WEEK_STRING[getDayOfWeek(date)];
	}

	/**
	 * 返回指定日期的一号
	 * 
	 * @param date
	 *            Date
	 * @return 一号的日期
	 */
	public static Calendar getFirstdayOfMonth(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c;
	}

	/**
	 * 得到当前月份月初 格式为：xxxx-yy-zz (eg: 2009-12-01)<br>
	 * 
	 * @return 格式化后的日期
	 */
	public static String getFirstDayOfMonth(final Date oDate) {
		int nYear; // 日期属性：年
		int nMonth; // 日期属性：月
		String strY = null;
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		nYear = c.get(Calendar.YEAR);
		nMonth = c.get(Calendar.MONTH) + 1;
		strY = nMonth >= 10 ? String.valueOf(nMonth) : ("0" + nMonth);
		return nYear + "-" + strY + "-01";
	}

	/**
	 * 求两个日期差
	 * 
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @return 两个日期相差小时数
	 */
	public static long getHourMargin(final Date beginDate, final Date endDate) {
		long margin = 0;

		margin = endDate.getTime() - beginDate.getTime();
		margin = margin / MILLISECONDS_OF_HOUR;

		return margin;
	}
	

	/**
	 * 根据日期获取日期中的小时数
	 * 
	 * @param oDate
	 *            日期
	 * @return 小时数
	 */
	public static int getHourOfDay(final Date oDate) {
		if (null == oDate) {
			return -1;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static Date getMaxDate(final Date date1, final Date date2) {
		if (Check.isEmpty(date1)) {
			return date2;
		} else if (Check.isEmpty(date2)) {
			return date1;
		} else {
			Date maxDate = date1;
			if (maxDate.getTime() < date2.getTime()) {
				maxDate = date2;
			}
			return maxDate;
		}
	}

	public static Date getMaxTime() {
		return makeDateTime(1970, 1, 1, 23, 59, 59, 0);
	}

	/**
	 * 根据日前时间获取毫秒值
	 * 
	 * @param timestamp
	 * @return
	 */
	public static long getMillisecondsByTimestamp(final Timestamp timestamp) {
		// return timestamp.getTime() + timestamp.getNanos() / 1000000;
		// timestamp.getNanos() 中包含毫秒和纳秒数，而Timestamp.getTime()终已包含毫秒数，经过试验测试，
		// 除去毫秒数之外的timestamp.getNanos() / 1000000 计算结果为0，等于没有执行，不需要再执行添加，
		// 因而不能再重复添加一次毫秒
		return timestamp.getTime();
	}

	/**
	 * 根据日期获取日期中的分钟数
	 * 
	 * @param oDate
	 *            日期
	 * @return 分钟数
	 */
	public static int getMinuteOfDay(final Date oDate) {
		if (null == oDate) {
			return -1;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 秒数转换为分钟
	 * 
	 * @param nSeconds
	 *            秒数
	 * @param n
	 *            (小数点后)精确位数
	 * @return 返回分钟数
	 */
	public static int getMinutes(final long nSeconds, final int n) {
		final BigDecimal big = new BigDecimal(nSeconds);
		final BigDecimal result = big.divide(new BigDecimal(60), n,
				RoundingMode.HALF_UP);

		return result.intValue();
	}

	/**
	 * 返回指定日期的周一(周一为一周的开始)
	 * 
	 * @param date
	 *            Date
	 * @return 周一的日期
	 */
	public static Calendar getMondayOfWeek(final Date date) {
		return getMondayOfWeek(date, Calendar.MONDAY);
	}

	/**
	 * 返回指定日期的周一
	 * 
	 * @param date
	 *            Date
	 * @return 周一的日期
	 */
	public static Calendar getMondayOfWeek(final Date date,
			final int bFirstDayOfWeek) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		int d = c.get(Calendar.DAY_OF_WEEK);
		if (bFirstDayOfWeek == Calendar.MONDAY) {
			d = Calendar.SUNDAY == d ? 6 : (d - Calendar.MONDAY);
		} else {
			d = Calendar.SUNDAY == d ? 0 : (d - Calendar.SUNDAY);
		}
		c.add(Calendar.DAY_OF_WEEK, 0 - d); // 计算周一
		return c;
	}

	/**
	 * 获取日期的月份
	 * 
	 * @param date
	 *            Date
	 * @return 日期的月份
	 */
	public static int getMonth(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前月份月底 格式为：xxxx-yy-zz (eg: 2009-12-31)<br>
	 * 
	 * @return 当前月份月底 格式的字符串
	 */
	public static String getMonthEnd(final Date oDate) {
		int nYear; // 日期属性：年
		int nMonth; // 日期属性：月

		String strMM = null; // 月字符串
		String strDD = null; // 天字符串
		boolean leap = false; // 是否是闰年
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		nYear = c.get(Calendar.YEAR);
		nMonth = c.get(Calendar.MONTH) + 1;
		if (nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7
				|| nMonth == 8 || nMonth == 10 || nMonth == 12) {
			strDD = "31";
		}
		if (nMonth == 4 || nMonth == 6 || nMonth == 9 || nMonth == 11) {
			strDD = "30";
		}
		if (nMonth == 2) {
			// 判断是否是闰年
			leap = leapYear(nYear);
			if (leap) {
				strDD = "29";
			} else {
				strDD = "28";
			}
		}
		strMM = nMonth >= 10 ? String.valueOf(nMonth) : ("0" + nMonth);
		return nYear + "-" + strMM + "-" + strDD;
	}

	/**
	 * 返回指定日期的周n
	 * 
	 * 返回的值为指定日期所在周几(1-7)
	 * 
	 * @see DateUtil#getDayOfWeek(Date)
	 * @param oDate
	 *            Date
	 * @param n
	 *            周
	 * @return 周N的日期
	 */
	public static Date getOneOfWeeks(final Date oDate, final int n) {
		final int nDay = ((n % 7) == 0) ? 7 : n % 7;

		int nDayOfWeek = DateUtil.getDayOfWeek(oDate); // 计算oDate是周几
		if (nDayOfWeek == 0) {
			// 如果nDayOfWeek == 0 则说明今天是周日，为一周的第七天
			nDayOfWeek = 7;
		}

		return addDays(oDate, nDay - nDayOfWeek);
	}

	/**
	 * 秒数字符串
	 * 
	 * @param seconds
	 *            秒
	 * @return 秒数; 格式 XX:XX
	 */
	public static String getPlayTimeString(final long seconds) {
		return timeLengthDescribe(seconds * 1000);
	}

	/**
	 * 毫秒数转换为(汉字)字符串
	 * 
	 * @param msec
	 *            毫秒
	 * @return 格式 XX时XX分XX秒
	 */
	@Deprecated
	public static String getPlayTimeStringCharacter(final long msec) {
		return TaskPerfomanceUtil.getTimeString(msec);
	}

	/**
	 * 根据日期获取日期中的秒数
	 * 
	 * @param oDate
	 *            日期
	 * @return 日期中的秒数
	 */
	public static int getSecondsOfDay(final Date oDate) {
		if (null == oDate) {
			return -1;
		}

		final Calendar cal = Calendar.getInstance();
		cal.setTime(oDate);

		return cal.get(Calendar.SECOND);
	}

	/**
	 * 毫秒数字符串
	 * 
	 * @param millis
	 *            毫秒
	 * @return 毫秒数; 格式 XX.X
	 */
	public static String getSecondsTimeString(final long millis) {
		int n = (int) millis + 50; // 四舍五入
		final int s = (n / (1000)); // 秒
		n %= (1000);
		n = n / 100;

		String sTime = Integer.toString(s);
		if (n > 0) {
			sTime += "." + Integer.toString(n);
		}
		return sTime;
	}

	/**
	 * 修改日期时间对象的时间部分为 00:00:00.0000
	 * 
	 * @param oDate
	 *            日期
	 * @return 修改时间部分后的日期
	 */
	public static java.sql.Date getSQLDate(final Date oDate) {
		if (null == oDate) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new java.sql.Date(c.getTime().getTime());
	}

	/**
	 * 修改日期时间对象的日期部分为 1970-01-01
	 * 
	 * @param oDate
	 *            日期
	 * @return 修改时间部分后的日期
	 */
	public static java.sql.Time getSQLTime(final Date oDate) {
		// if (null == oDate)
		// {
		// return null;
		// }
		// final Calendar c = Calendar.getInstance();
		// c.setTime(oDate);
		// c.set(Calendar.YEAR, 1970);
		// c.set(Calendar.MONTH, 0);
		// c.set(Calendar.DAY_OF_MONTH, 1);
		// return new java.sql.Time(c.getTime().getTime());
		return new java.sql.Time(oDate.getTime());
	}

	/**
	 * 根据毫秒数值计算时分秒数值 采用递归调用
	 * 
	 * @param time
	 *            毫秒数值
	 * @return
	 */
	public static String getTime(final long time) {
		if (time <= 0) {
			return "";
		}
		if (time < SECOND) {
			return time + "ms";
		} else if (time < MINUTE) {
			return (time / SECOND) + "s" + getTime(time % SECOND);
		} else if (time < HOUR) {
			return (time / MINUTE) + "m" + getTime(time % MINUTE);
		} else if (time >= HOUR) {
			return (time / HOUR) + "h" + getTime(time % HOUR);
		}
		return "";
	}

	/**
	 * 修改日期时间对象的日期部分为 1970-01-01
	 * 
	 * @param oDate
	 *            日期
	 * @return 修改时间部分后的日期
	 */
	public static Date getTimeOnly(final Date oDate) {
		if (null == oDate) {
			return null;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		c.set(Calendar.YEAR, 1970);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}

	/**
	 * 获取日期的年份
	 * 
	 * @param date
	 *            Date
	 * @return 日期的年份
	 */
	public static int getYear(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 获取日期的年份月份
	 * 
	 * @param date
	 *            日期
	 * @return 年份月份
	 */
	public static int getYearMonth(final Date date) {
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR) * 100 + c.get(Calendar.MONTH) + 1;
	}

	/**
	 * 修改日期时间对象的时间部分为 00:00:00.0000
	 * 
	 * @param oDate
	 *            日期
	 * @return 修改时间部分后的日期
	 */
	public static Date getZeroDate(final Date oDate) {
		if (null == oDate) {
			return oDate;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 修改日期时间对象的毫秒（和秒）部分为 0000
	 * 
	 * @param oDate
	 *            日期
	 * @param second
	 *            是否将秒也清零
	 * @return 修改时间部分后的日期
	 */
	public static Date getZeroDateTime(final Date oDate, final boolean second) {
		if (null == oDate) {
			return oDate;
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(oDate);

		if (second) {
			c.set(Calendar.SECOND, 0);
		}

		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	// /**
	// * 填充中间缺失小时的日历，返回天连续的日历List，填充的日历是上一小时加一小时的日历
	// *
	// * @param cl
	// * 日历串
	// * @return 返回天连续的日历List
	// */
	// @Deprecated
	// public static List<Calendar> addDeletionHours(final List<Calendar> cl)
	// {
	// final List<Calendar> rcl = new ArrayList<Calendar>();
	// int y = 1;
	// for (final Calendar c : cl)
	// {
	// final Calendar h = Calendar.getInstance();
	//
	// if (y >= cl.size())
	// {
	// h.setTime(cl.get(cl.size() - 1).getTime());
	// rcl.add(h);
	// break;
	// }
	// else
	// {
	// h.setTime(cl.get(y).getTime());
	// }
	//
	// h.add(Calendar.HOUR_OF_DAY, -1);
	//
	// if (c.get(Calendar.YEAR) == h.get(Calendar.YEAR) && c.get(Calendar.MONTH)
	// == h.get(Calendar.MONTH)
	// && c.get(Calendar.DAY_OF_MONTH) == h.get(Calendar.DAY_OF_MONTH)
	// && c.get(Calendar.HOUR_OF_DAY) == h.get(Calendar.HOUR_OF_DAY))
	// {
	// rcl.add(c);
	// y++;
	// continue;
	// }
	// else
	// {
	// //时间在之后才计算是否缺时
	// if (h.after(c))
	// {
	// final double ddays = (h.getTimeInMillis() - c.getTimeInMillis()) /
	// (MILLISECONDS_OF_DAY);
	// final int days = (int) ddays + 1;
	// for (int i = 0; i <= days; i++)
	// {
	// final Calendar tmp = Calendar.getInstance();
	// tmp.setTime(c.getTime());
	// tmp.add(Calendar.HOUR_OF_DAY, i);
	// rcl.add(tmp);
	// }
	// }
	// //时间在之前则就不算缺时，以此为起点重新开始计算
	// else
	// {
	// rcl.add(c);
	// y++;
	// continue;
	// }
	// }
	// y++;
	// }
	// return rcl;
	// }

	/**
	 * 工作时间
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isWorking(final Date date) {
		final int hourOfDay = getHourOfDay(date);
		if (hourOfDay > 8 && hourOfDay < 19) {
			return true;
		}
		return false;
	}

	/**
	 * 判断输入年份是否为闰年<br>
	 * 
	 * @param year
	 *            年
	 * @return 是：true 否：false
	 */
	public static boolean leapYear(final int year) {
		boolean leap;
		if (year % 4 == 0) {
			if (year % 100 == 0) {
				if (year % 400 == 0) {
					leap = true;
				} else {
					leap = false;
				}
			} else {
				leap = true;
			}
		} else {
			leap = false;
		}
		return leap;
	}

	/**
	 * 将指定的日历设置指定的值,修改日期时间对象的时间部分为 00:00:00.0000
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return 设置后的日历
	 */
	public static Date makeDate(final int year, final int month, final int day) {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 制作日期时间对象
	 * 
	 * @param date
	 *            日期
	 * @param time
	 *            日期
	 * @return 设置后的日期
	 */
	public static Date makeDateTime(final Date date, final Date time) {
		final Calendar c = Calendar.getInstance();
		final Calendar t = Calendar.getInstance();

		if (null == date) {
			c.set(Calendar.YEAR, 1970);
			c.set(Calendar.MONTH, 0);
			c.set(Calendar.DAY_OF_MONTH, 1);
		} else {
			c.setTime(date);
		}
		t.setTime(time);

		c.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
		c.set(Calendar.SECOND, t.get(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));

		return c.getTime();
	}

	/**
	 * 制作日期时间对象
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param milliSecond
	 *            毫秒
	 * @return 构造成的日期对象
	 */
	public static Date makeDateTime(final int year, final int month,
			final int day, final int hour, final int minute, final int second,
			final int milliSecond) {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, milliSecond);
		return c.getTime();
	}

	/**
	 * 制作时间对象(日期部分为 1970.1.1)
	 * 
	 * @param hour
	 *            时
	 * @param minute
	 * @return 根据参数构造成新的日期对象
	 */
	public static Date makeTime(final int hour) {
		return makeTime(hour, 0, 0, 0);
	}

	/**
	 * 制作时间对象(日期部分为 1970.1.1)
	 * 
	 * @param hour
	 *            时
	 * @param minute
	 *            分
	 * @param second
	 *            秒
	 * @param milliSecond
	 *            毫秒
	 * @return 根据参数构造成新的日期对象
	 */
	public static Date makeTime(final int hour, final int minute,
			final int second, final int milliSecond) {
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, 1970);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		c.set(Calendar.SECOND, second);
		c.set(Calendar.MILLISECOND, milliSecond);
		return c.getTime();
	}

	/**
	 * 
	 * @param nano
	 * @return
	 */
	public static long nanoToMillis(final long nano) {
		return nano / 1000000L;
	}

	public static String timeLengthDescribe(final long mils) {
		int n = (int) mils / 1000;
		final int h = (n / (60 * 60)); // 时
		n %= (60 * 60);
		final int m = n / 60; // 分
		n %= 60;
		final int s = n; // 秒

		String sh = Integer.toString(h);
		if (sh.length() < 2) {
			sh = "0" + sh;
		}
		String sm = (Integer.toString(m));
		if (sm.length() < 2) {
			sm = "0" + sm;
		}
		String ss = (Integer.toString(s));
		if (ss.length() < 2) {
			ss = "0" + ss;
		}

		String sTime = "";
		if (h > 0) {
			sTime += sh + ":";
		}
		sTime += sm + ":";
		return sTime + ss;
	}

	/**
	 * 日期时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @return 日期时间字符串(DEFAULT_DATETIME_FORMAT)
	 */
	public static String timestampToString(final Date date) {
		return dateTimeToString(date, DEFAULT_TIMESTAMP_FORMAT);
	}

	/**
	 * 时间转字符串(JavaScript 格式)
	 * 
	 * @param date
	 *            Date
	 * @return 日期字符串(JavaScript 格式)
	 */
	public static String timeToJSString(final Date date) {
		return dateTimeToString(date, JS_TIME_FORMAT);
	}

	/**
	 * 时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @return 时间字符串(DEFAULT_TIME_FORMAT)
	 */
	public static String timeToString(final Date date) {
		return dateTimeToString(date, DEFAULT_TIME_FORMAT);
	}

	/**
	 * 时间转字符串
	 * 
	 * @param date
	 *            Date
	 * @param format
	 *            String
	 */
	public static String timeToString(final Date date, final String format) {
		return dateTimeToString(date, format);
	}
	
	/**
	* 字符串转换成日期
	* @param str
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}

	/**
	 * 时间转字符串(HH:mm)
	 * 
	 * @param date
	 *            Date
	 * @return 时间字符串(HOURMINUTES_TIME_FORMAT)
	 */
	public static String timeToStringHourMinutes(final Date date) {
		return dateTimeToString(date, HOURMINUTES_TIME_FORMAT);
	}

	/**
	 * 私有构造函数防止被构建
	 */
	private DateUtil() {
	}
}
