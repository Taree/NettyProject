/* ===========================================================================
 * $                                                                         $
 * $ (C) Copyright http://www.uuwatch.com 2007-2008  All rights reserved.    $
 * $                                                                         $
 * ===========================================================================
 * $Revision:: 71332                                                         $ 
 * $LastChangedBy:: ycq                                                      $ 
 * $LastChangedDate:: 2014-08-28 16:51:34 +0800 #$                           $
 * ===========================================================================
 */

package com.baifendian.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 日期时间辅助程序
 * 
 * @author caviler
 * @author ice
 * @author edwin
 */
public class DateParser {
	private static Pattern DAY_REGEX = Pattern.compile("\\d*?-\\d*?-\\d*?");

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

	public static final String DEFAULT_DATE_FORMAT2 = "yyyy.MM.dd";

	public static final String DEFAULT_DATE_FORMAT3 = "yyyy年MM月dd日";

	public static final String DEFAULT_DATE_FORMAT4 = "yyyy年MM月";

	public static final String DEFAULT_DATE_FORMAT5 = "yyyyMMdd";

	public static final String DEFAULT_DATE_FORMAT6 = "yyyyMM";

	public static final String DEFAULT_DATE_FORMAT7 = "yyyy";

	public static final String DEFAULT_DATE_FORMAT8 = "yyyy-M-d";

	public static final String DEFAULT_DATE_FORMAT9 = "yyyy年MM月dd日   时间：HH:mm";

	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss"; // 24小时时间格式

	public static final String DEFAULT_DATETIME_FORMAT_NO_YEAR = "MM-dd HH:mm:ss";

	public static final String DEFAULT_DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm"; // 24小时时间格式

	public static final String DEFAULT_DATETIME_FORMAT3 = "MM-dd HH:mm"; // 24小时时间格式

	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss"; // 24小时时间格式

	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"; // 24小时时间格式,包含毫秒

	protected static final String DIAN = "点";

	private static final String ENGILSH_DAY = "day";

	private static final String ENGILSH_MONTH = "month";

	private static final String ENGILSH_TIME = "time";

	private static final String ENGILSH_TIME_HAS_SECOND = "time_has_second";

	private static final String ENGILSH_TIME_NO_SECOND = "time_no_second";

	private static final String ENGILSH_WAY = "am/pm";

	private static final String ENGILSH_WEEK = "week";

	private static final String ENGILSH_YEAR = "year";

	private static final String ENGILSH_ZONE = "zone";

	protected static final String FEN = "分";

	protected static final String MIAO = "秒";

	protected static final String NIAN = "年";

	protected static final String QIAN = "前";

	protected static final String RI = "日";

	protected static final String SHI = "时";

	protected static final String TIAN = "天";

	protected static final String XIAOSHI = "小时";

	protected static final String YUE = "月";

	// 将字符转成数字
	private static int conversionCase(final char c) {
		if (c == '零') {
			return 0;
		}
		if (c == '〇') {
			return 0;
		}
		if (c == '一') {
			return 1;
		}
		if (c == '二') {
			return 2;
		}
		if (c == '三') {
			return 3;
		}
		if (c == '四') {
			return 4;
		}
		if (c == '五') {
			return 5;
		}
		if (c == '六') {
			return 6;
		}
		if (c == '七') {
			return 7;
		}
		if (c == '八') {
			return 8;
		}
		if (c == '九') {
			return 9;
		}
		if (c == '十') {
			return 10;
		}

		return 0;
	}

	/**
	 * 根据时间字符串和时间类型进行相应转换
	 * 
	 * @param s
	 * @param type
	 * @return
	 */
	private static Map<String, Boolean> format(final String str,
			final int type, final String timeZone, final String format) {
		String result = "";
		boolean guess = false;
		final Map<String, Boolean> map = new HashMap<String, Boolean>();
		if (type == 1) {
			result = formatDayAndTime(str);
		} else if (type == 2) {
			result = formatYear(str);
		} else if (type == 3) {
			result = formatTheDayBeforYesterday(str);
		} else if (type == 4) {
			result = formatBeforHalfHour(str);
		} else if (type == 5) {
			result = formatYesterday(str);
		} else if (type == 6) {
			final Map<String, Boolean> dmap = formatEnlishDate(str, timeZone,
					format);
			for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
				result = entry.getKey();
				guess = entry.getValue();
			}
		} else if (type == 7) {
			result = formatChineseDate(str);
		} else if (type == 8) {
			result = getNowTime();
		}
		map.put(result, guess);
		return map;
	}

	/**
	 * 将时间字符串格式化
	 * 
	 * @param s
	 * @return
	 */
	// private static Map<String, Boolean> format(final String s,
	// final String timeZone, final String format, final Date baseTime) {
	// String str = separateTimeAndDateBySpace(s);
	//
	// str = formatNormalTime(str);
	// boolean guess = false;
	//
	// final int type = parseTimeFormat(str);
	//
	// final Map<String, Boolean> map = new HashMap<String, Boolean>();
	//
	// if (type > 0) {
	// return format(str, type, timeZone, format);
	// }
	// try {
	// str = formatNormal(str, baseTime);
	// } catch (final Throwable e) {
	// map.put("", false);
	// return map;
	// }
	//
	// if (str.indexOf(RI) != str.lastIndexOf(RI)) {
	// str = str.replaceFirst(RI, YUE);
	// }
	//
	// // 过滤字符串中的中文 替换为相应的字符
	// str = str.replace(NIAN, "-");
	// str = str.replace(YUE, "-");
	// str = str.replace(RI, "");
	// str = str.replace(SHI, ":");
	// str = str.replace(DIAN, ":");
	// str = str.replace(FEN, ":");
	// str = str.replace(MIAO, "");
	//
	// str = str.replaceAll("/", "-");
	//
	// str = str.replaceAll("\\s?-\\s?", "-");
	// // str = str.replace(" -", "-");
	// str = str.replaceAll("\\s星期\\W", "");
	//
	// str = removeUnuseInfo(str);
	//
	// final Map<String, Boolean> dmap = formatComplete(str, format);
	// for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
	// str = entry.getKey();
	// guess = entry.getValue();
	// }
	//
	// map.put(str, guess);
	// return map;
	// }

	/**
	 * 将几年前，几月前，几天前类型转为日期格式字符串
	 * 
	 * @param strTime
	 * @return
	 */
	private static String formatBeforDate(final String strTime,
			final Date baseTime) {
		String result = null;
		Date newday = new Date();
		if (strTime.indexOf(NIAN) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(NIAN);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int year = Integer.parseInt(m.group(1));
				newday = DateUtil.addYears(baseTime, -year);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-01-01 00:00:00");
			result = df.format(newday);
		} else if (strTime.indexOf(YUE) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(YUE);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int month = Integer.parseInt(m.group(1));
				newday = DateUtil.addMonths(baseTime, -month);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-MM-01 00:00:00");
			result = df.format(newday);
		} else if (strTime.indexOf(TIAN) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(TIAN);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int day = Integer.parseInt(m.group(1));
				newday = DateUtil.addDays(baseTime, -day);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			result = df.format(newday);
		}
		return result;
	}

	/**
	 * 格式化“半小时前”
	 * 
	 * @param str
	 * @return
	 */
	private static String formatBeforHalfHour(final String str) {
		String result = "";
		final Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, -30);
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		result = df.format(date.getTime());
		return result;
	}

	/**
	 * 将几小时前，几分钟前，几秒前转换为日期格式字符串
	 * 
	 * @param strTime
	 * @return
	 */
	private static String formatBeforTime(final String strTime,
			final Date baseTime) {
		String result = null;
		Date newdate = new Date();
		if (strTime.indexOf(XIAOSHI) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(XIAOSHI);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int hour = Integer.parseInt(m.group(1));
				newdate = DateUtil.addHours(baseTime, -hour);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
			result = df.format(newdate);
		} else if (strTime.indexOf(FEN) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(FEN);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int minute = Integer.parseInt(m.group(1));
				newdate = DateUtil.addMinutes(baseTime, -minute);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
			result = df.format(newdate);
		} else if (strTime.indexOf(MIAO) != -1) {
			final StringBuilder psb = new StringBuilder();
			psb.append("(\\d*?)\\s*?");
			psb.append(MIAO);

			final String patternStr = psb.toString();
			final Pattern p = Pattern.compile(patternStr);
			final Matcher m = p.matcher(strTime);
			if (m.find()) {
				final int sec = Integer.parseInt(m.group(1));
				newdate = DateUtil.addSeconds(baseTime, -sec);
			}
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			result = df.format(newdate);
		}
		return result;
	}

	private static String formatChineseDate(final String strTime) {
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String time = " 00:00:00";
		String day = "";
		if (strTime.contains("前天")) {
			day = df.format(DateUtil.addDays(new Date(), -2));
		} else if (strTime.contains("昨天")) {
			day = df.format(DateUtil.addDays(new Date(), -1));
		} else if (strTime.contains("今天")) {
			day = df.format(new Date());
		}
		if (strTime.trim().length() > 2) {
			final String[] value = strTime.split(" ");
			if (value.length > 1) {
				time = " " + getCompleteTime(value[1], false);

			}

		}
		return day + time;
	}

	private static String formatChineseTime(final String time) {
		final String regex = "(.*)年(.*)月(.*)日([\\S\\s]*)";
		final Pattern p = Pattern.compile(regex);
		final Matcher m = p.matcher(time);
		final StringBuilder sb = new StringBuilder();
		if (m.find()) {
			final int year = getTimeNum(m.group(1));
			if (year > 0) {
				sb.append(year + "年");
			}
			final int month = getTimeNum(m.group(2));
			if (month > 0) {
				sb.append(month + "月");
			}
			final int day = getTimeNum(m.group(3));
			if (day > 0) {
				sb.append(day + "日");
			}
			final String time1 = m.group(4);
			if (Check.notEmpty(time1)) {
				sb.append(time1);
			}
		}
		return sb.toString();
	}

	/**
	 * 将缺省的时间补充完整
	 * 
	 * @param str
	 * @return
	 */
	private static Map<String, Boolean> formatComplete(final String str,
			final String format) {
		final String[] time = str.split(" +");

		String sdate = "";
		boolean guess = false;

		final Map<String, Boolean> map = new HashMap<String, Boolean>();

		StringBuilder value = new StringBuilder();
		if (time.length >= 2) {
			final Map<String, Boolean> dmap = getCompleteDate(time[0], format);
			for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
				sdate = entry.getKey();
				guess = entry.getValue();
			}

			boolean isPm = false;
			if (time.length == 3) {
				if ("pm".endsWith(time[2])) {
					isPm = true;
				} else {
					final Pattern p = Pattern
							.compile("\\d{4}\\s\\d{1,2}\\s\\d{1,2}");
					final Matcher m = p.matcher(str);
					if (m.find()) {
						final String stime = time[0] + "-" + time[1] + "-"
								+ time[2] + " 00:00:00";
						map.put(stime, false);
						return map;
					}
				}
			}

			value.append(sdate);
			value.append(" ");
			value.append(getCompleteTime(time[1], isPm));

			if (time.length == 3 && !"pm".equals(time[2])
					&& !"am".equals(time[2])) {
				value.append(" " + time[2]);
			}
		} else {
			if (str.contains("-")) {
				final Map<String, Boolean> dmap = getCompleteDate(time[0],
						format);
				for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
					sdate = entry.getKey();
					guess = entry.getValue();
				}

				value.append(sdate);
				value.append(" 00:00:00");
			} else {
				final Date date = new Date();

				StringBuilder dateSb = new StringBuilder();
				dateSb.append(DateUtil.getYear(date));
				dateSb.append("-");
				dateSb.append(DateUtil.getMonth(date));
				dateSb.append("-");
				dateSb.append(DateUtil.getDay(date));

				final Map<String, Boolean> dmap = getCompleteDate(
						dateSb.toString(), format);
				for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
					sdate = entry.getKey();
					guess = entry.getValue();
				}

				value.append(sdate);
				value.append(" ");
				value.append(getCompleteTime(time[0], false));

				if (!isRightTime(value.toString())) {
					dateSb = new StringBuilder();
					dateSb.append(DateUtil.getYear(date));
					dateSb.append("-");
					dateSb.append(DateUtil.getMonth(date));
					dateSb.append("-");
					dateSb.append((DateUtil.getDay(date) - 1));

					value = new StringBuilder();
					value.append(sdate);
					value.append(" ");
					value.append(getCompleteTime(time[0], false));
				}
			}
		}
		map.put(value.toString(), guess);
		return map;
	}

	/**
	 * 格式为 "天[\\s\\S]*?xx:xx"的字符串解析
	 * 
	 * @param str
	 * @return
	 */
	private static String formatDayAndTime(final String str) {
		String result = "";

		String patternStr = "(\\d{1,2})[^\\d]{1}(\\d{1,2})";
		Pattern p = Pattern.compile(patternStr);
		Matcher m = p.matcher(str);
		if (m.find()) {
			final String hourStr = m.group(1);
			final String minuteStr = m.group(2);
			try {
				final int hour = Integer.parseInt(hourStr);
				final int minute = Integer.parseInt(minuteStr);
				final Calendar date = Calendar.getInstance();
				date.set(Calendar.HOUR_OF_DAY, hour);
				date.set(Calendar.MINUTE, minute);
				date.set(Calendar.SECOND, 0);
				if (str.indexOf("前天") != -1) {
					date.add(Calendar.DATE, -2);
				} else if (str.indexOf("昨天") != -1) {
					date.add(Calendar.DATE, -1);
				} else {
					patternStr = "(\\d*?)[^\\d]*?天前";
					p = Pattern.compile(patternStr);
					m = p.matcher(str);
					if (m.find()) {
						final int day = Integer.parseInt(m.group(1));
						date.add(Calendar.DATE, -day);
					}
				}
				final DateFormat df = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				result = df.format(date.getTime());
			} catch (final Throwable e) {
				return "";
			}
		}

		return result;
	}

	/**
	 * 格式化 日期和时间 即 将日期 与时间 用空格分开
	 * 
	 * @param str
	 * @return
	 */
	private static String formatDayTime(final String str) {
		final StringBuilder result = new StringBuilder();
		int nump = 0;
		int numh = 0;
		int numm = 0;
		int mstart = 0;
		boolean isFirst = true;
		boolean haveSpace = false;
		char lastc = ' ';
		for (int i = 0; i < str.length(); i++) {
			final char c = str.charAt(i);
			final char space = ' ';
			if (c == '/') {
				nump++;
			} else if (c == '-') {
				numh++;
			} else if (c == ':') {
				numm++;
				if (numm == 1) {
					mstart = i;
				}

			} else if (c == space) {
				if (lastc == space) {
					continue;
				}
				haveSpace = true;
			}
			if ((nump == 3 || numh == 3) && isFirst && !haveSpace) {
				result.append(space);
				isFirst = false;
				continue;
			}
			if (numm == 3) {
				result.setCharAt(mstart, space);
			}
			result.append(c);
			lastc = c;
		}
		return result.toString();
	}

	/**
	 * 格式化英文时间
	 * 
	 * @param strTime
	 * @return
	 */
	private static Map<String, Boolean> formatEnlishDate(final String strTime,
			final String timezone, final String format) {
		if (Check.isEmpty(strTime)) {
			return null;
		}

		final Map<String, Boolean> map = new HashMap<String, Boolean>();
		String time = strTime.toLowerCase().trim();
		time = removeUnuseInfo(time);
		// if (time.indexOf("-") != -1 && time.indexOf("-") ==
		// time.lastIndexOf("-"))
		// {
		if (!DAY_REGEX.matcher(time).find()
				|| time.indexOf("-") == time.lastIndexOf("-")) {
			time = time.replace("-", " ");
		}
		// }

		final String[] strs = time.split(" ");
		final Map<String, String> timeContainer = new HashMap<String, String>();

		// 假的新闻时间，获取正确的时间
		final StringBuilder newtime = new StringBuilder();

		if (time.contains("-")) {
			parseDateFromEnlish(strs, newtime, timezone);
			return formatComplete(newtime.toString(), format);
		}
		// 拆分英文时间
		for (String str : strs) {
			if (Check.isEmpty(str)) {
				continue;
			}
			// 处理 2012: 这个类型的数据
			if (str.endsWith(":")) {
				str = str.replace(":", "");
			}

			final char start = str.charAt(0);
			if (str.contains(":")) // 判断时间
			{
				if (str.indexOf(":") == str.lastIndexOf(":")) {
					timeContainer.put(ENGILSH_TIME_NO_SECOND, str);
				} else {
					timeContainer.put(ENGILSH_TIME_HAS_SECOND, str);
				}

				timeContainer.put(ENGILSH_TIME, ENGILSH_TIME);
				continue;
			}

			if (start >= '0' && start <= '9') {
				if (str.endsWith("th") || str.endsWith("rd")
						|| str.endsWith("st")) {
					str = str.substring(0, str.length() - 2);
				}
				if (str.length() <= 2) // 判断日
				{
					timeContainer.put(ENGILSH_DAY, str);
					continue;
				}

				if (str.length() == 4) // 判断年
				{
					timeContainer.put(ENGILSH_YEAR, str);
					continue;
				}

			}

			if (start >= 'a' && start <= 'z') {
				if (isWeek(str)) {
					timeContainer.put(ENGILSH_WEEK, str); // 判断星期
					continue;
				}

				if (isMonth(str)) {
					// 修正原页面拼写错误
					if ("junl".equals(str)) {
						str = "july";
					}
					timeContainer.put(ENGILSH_MONTH, str); // 判断星期
					continue;
				}

				if (str.length() == 2 && str.endsWith("m")) // 判断方式
				{
					timeContainer.put(ENGILSH_WAY, str);
					continue;
				}

				if ("et".equals(str)) {
					str = "est";
				}
				if (str.length() == 3) // 判断时区
				{
					timeContainer.put(ENGILSH_ZONE, str);
					continue;
				}

			}
		}

		if (!timeContainer.containsKey(ENGILSH_ZONE)
				&& Check.notEmpty(timezone)) {
			timeContainer.put(ENGILSH_ZONE, timezone);
		}

		if (strTime.indexOf(":") == -1) {
			timeContainer.put(ENGILSH_TIME, null);
		}

		final StringBuilder date = new StringBuilder();
		final StringBuilder dateFormat = new StringBuilder();

		getFormatter(timeContainer, date, dateFormat);

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat.toString(),
				Locale.ENGLISH);
		Date result;
		try {
			result = sdf.parse(date.toString());
		} catch (final ParseException e) {
			map.put("", false);
			return map;
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		map.put(sdf.format(result), false);
		return map;

	}

	/**
	 * 将date(年、月、天)前、time(小时、分、秒)前的时间字符串格式化
	 * 
	 * @param strTime
	 * @return
	 */
	private static String formatNormal(final String strTime, final Date baseTime) {
		String result = null;
		if (strTime.indexOf(QIAN) != -1) {
			Date base = baseTime;
			if (baseTime == null) {
				base = new Date();
			}

			if (strTime.indexOf(NIAN) != -1 || strTime.indexOf(YUE) != -1
					|| strTime.indexOf(TIAN) != -1) {
				result = formatBeforDate(strTime, base);
			} else if (strTime.indexOf(XIAOSHI) != -1
					|| strTime.indexOf(FEN) != -1
					|| strTime.indexOf(MIAO) != -1) {
				result = formatBeforTime(strTime, base);
			}
		}

		// 将文字的时间转为数字
		else if (StringUtil.getStringLength(strTime.substring(0, 1)) == 2) {
			result = formatChineseTime(strTime);
		}
		if (Check.isEmpty(result)) {
			result = strTime;
		}

		result = result.replace(" - ", " ");

		return result;
	}

	private static String formatNormalTime(final String time) {
		String stime = time.toLowerCase();

		final String[] times = stime.split(" ");
		if (times.length == 2) {
			final String item = times[0];
			if (item.contains(":")) {
				stime = times[1] + " " + times[0];
			}
		}

		if (stime.contains("a.m.") || stime.contains("p.m.")) // 格式化
																// a.m./p.m.为am/pm
		{
			stime = stime.replace(".m.", "m");
		}

		int point = 0;
		if (stime.contains("am")) {
			point = stime.indexOf("am");
			if (stime.charAt(point - 1) != ' ') {
				stime = stime.replace("am", " am");
			}
		} else if (stime.contains("pm")) {
			point = stime.indexOf("pm");
			if (stime.charAt(point - 1) != ' ') {
				stime = stime.replace("pm", " pm");
			}
		}

		if (point > 0
				&& (stime.charAt((point - 3)) == '.' || stime
						.charAt((point - 4)) == '.')) {
			stime = stime.replace(".", ":");
		}

		final Pattern p = Pattern.compile("\\d+[\\./／]\\d+");
		final Matcher m = p.matcher(stime);
		if (m.find()) {
			stime = stime.replaceAll("[\\./／]", "-");
		}
		stime = stime.replace(".", "");

		if (stime.indexOf("上午") > -1) {
			stime = stime.replace("上午", "").concat(" am");
		}
		if (stime.indexOf("下午") > -1) {
			stime = stime.replace("下午", "").concat(" pm");
		}

		return stime;
	}

	/**
	 * 格式化“前天”
	 * 
	 * @param str
	 * @return
	 */
	private static String formatTheDayBeforYesterday(final String str) {
		String result = "";
		final Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -2);
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		result = df.format(date.getTime());
		return result;
	}

	/**
	 * 时间格式为 "xxxx年"或"xxxx"或"xx年"或"xx"
	 * 
	 * @param str
	 * @return
	 */
	private static String formatYear(final String str) {
		String result = "";

		final String patternStr = "(\\d{2,4})";
		final Pattern p = Pattern.compile(patternStr);
		final Matcher m = p.matcher(str);
		final Calendar date = Calendar.getInstance();
		if (m.find()) {
			int year = Integer.parseInt(m.group(1));
			year = getFullYear(year);
			date.set(Calendar.YEAR, year);
			date.set(Calendar.MONTH, 0);
			date.set(Calendar.DATE, 1);
			final DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			result = df.format(date.getTime());
		}
		return result;
	}

	/**
	 * 格式化"昨天"
	 * 
	 * @param str
	 * @return
	 */
	private static String formatYesterday(final String str) {
		String result = "";
		final Calendar date = Calendar.getInstance();
		date.add(Calendar.DATE, -1);
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		result = df.format(date.getTime());
		return result;
	}

	/**
	 * 得到完整的日期类型
	 * 
	 * @param str
	 * @return
	 */
	private static Map<String, Boolean> getCompleteDate(final String str,
			final String format) {
		String date = "";
		boolean guess = false;
		final StringBuilder value = new StringBuilder();
		final String[] array = str.split("-");
		final Calendar calerdar = Calendar.getInstance();
		final Map<String, Boolean> map = new HashMap<String, Boolean>();

		if (array.length == 3 && Check.notEmpty(format)) {
			date = DateUtil.dateTimeToString(DateUtil.parseDate(str, format),
					DateUtil.DEFAULT_DATE_FORMAT);
			map.put(date, guess);
			return map;
		}

		if (array.length < 3) {
			if (array.length == 2 && array[0].length() < 3) {
				final Date today = new Date();
				// 与当前时间比 超过1个月的 认为是去年的新闻
				if ((Integer.parseInt(array[0]) == DateUtil.getMonth(today) + 1 && Integer
						.parseInt(array[1]) > calerdar.get(Calendar.DATE))
						|| Integer.parseInt(array[0]) > DateUtil
								.getMonth(today) + 1) {
					value.append(calerdar.get(Calendar.YEAR) - 1);
				} else {
					value.append(DateUtil.getYear(new Date()));
				}
				value.append("-");
			}
		}

		if (array.length == 3 && (array[2].length() == 4)) {
			String orig = array[0];
			array[0] = array[2];
			array[2] = orig;

			if (Integer.parseInt(array[1]) > 12
					|| Integer.parseInt(array[0]) == calerdar
							.get(Calendar.YEAR)
					&& Integer.parseInt(array[1]) > calerdar
							.get(Calendar.MONTH) + 1) {
				orig = array[1];
				array[1] = array[2];
				array[2] = orig;
			}
			guess = true;

		}

		// 处理 时间如：12-0920-11 将0920拆分成月日
		if (array.length == 3 && array[1].length() == 4) {
			array[2] = array[1].substring(2);
			array[1] = array[1].substring(0, 2);
			guess = true;

		}

		for (int i = 0; i < array.length; i++) {
			String s = array[i];
			final StringBuilder dateSb = new StringBuilder();

			// yyyy-MM-dd 格式
			if (array.length == 3) {
				if (i == 0 && s.length() == 2) {
					final int year = Integer.parseInt(s);
					s = String.valueOf(getFullYear(year));
				}
			}

			if (s.length() == 1) {
				dateSb.append("0");
				dateSb.append(s);
				s = dateSb.toString();
			}

			value.append(s);
			value.append("-");
		}

		// 用于仅有年月，日期系统补1
		if (value.length() < 11) {
			value.append("01");
			value.append("-");
		}
		date = value.toString().substring(0, value.length() - 1);

		map.put(date, guess);
		return map;
	}

	/**
	 * 得到完整的时间类型
	 * 
	 * @param str
	 * @return
	 */
	private static String getCompleteTime(final String str, final boolean isPm) {
		String date = "";

		// 处理时间类型为 12::24:(12时:24分;中国企业网)
		final String str2 = str.replace("::", ":");
		StringBuilder value = new StringBuilder();
		final String[] array = str2.split(":");

		if (isPm) {
			final int h = Integer.valueOf(array[0]);
			if (h < 12) {
				array[0] = String.valueOf((h + 12));
			}
		}

		for (String s : array) {
			final StringBuilder sb = new StringBuilder();

			if (s.length() == 1) {
				sb.append("0");
				sb.append(s);
				s = sb.toString();
			}
			value.append(s);
			value.append(":");
		}

		final StringBuilder tempValue = value;
		value = new StringBuilder();
		value.append(tempValue.toString().substring(0, tempValue.length() - 1));

		if (array.length == 2) {
			if (Integer.parseInt(array[0]) < 24) {
				value.append(":00");
			} else {
				value = new StringBuilder();
				value.append(DateUtil.getHourOfDay(new Date()));
				value.append(":");
				value.append(tempValue.toString());
			}

		}
		date = value.toString();
		return date;
	}

	private static String getFormatter(final Map<String, String> timeContainer,
			final StringBuilder date, final StringBuilder dateFormat) {
		if (timeContainer.containsKey(ENGILSH_WEEK)) {
			dateFormat.append("EEEE");
			dateFormat.append(" ");
			date.append(timeContainer.get(ENGILSH_WEEK));
			date.append(" ");
		}

		if (timeContainer.containsKey(ENGILSH_MONTH)) {
			dateFormat.append("MMMM");
			dateFormat.append(" ");
			date.append(timeContainer.get(ENGILSH_MONTH));
			date.append(" ");
		} else {
			dateFormat.append("MMMM");
			dateFormat.append(" ");
			date.append(DateUtil.getMonth(new Date()));
			date.append(" ");
		}

		if (timeContainer.containsKey(ENGILSH_DAY)) {
			dateFormat.append("dd");
			dateFormat.append(" ");
			date.append(timeContainer.get(ENGILSH_DAY));
			date.append(" ");
		} else {
			dateFormat.append("dd");
			dateFormat.append(" ");
			date.append(DateUtil.getDay(new Date()));
			date.append(" ");
		}
		if (timeContainer.containsKey(ENGILSH_YEAR)) {
			dateFormat.append("yyyy");
			dateFormat.append(" ");
			date.append(timeContainer.get(ENGILSH_YEAR));
			date.append(" ");
		} else {
			dateFormat.append("yyyy");
			dateFormat.append(" ");
			date.append(DateUtil.getYear(new Date()));
			date.append(" ");
		}

		final boolean has_time_way = timeContainer.containsKey(ENGILSH_WAY);

		if (Check.notEmpty(timeContainer.get(ENGILSH_TIME))) {
			if (has_time_way) {
				dateFormat.append("hh");
			} else {
				dateFormat.append("HH");
			}

			if (timeContainer.containsKey(ENGILSH_TIME_HAS_SECOND)) {
				dateFormat.append(":mm:ss");
				date.append(timeContainer.get(ENGILSH_TIME_HAS_SECOND));
			}

			if (timeContainer.containsKey(ENGILSH_TIME_NO_SECOND)) {
				dateFormat.append(":mm");
				date.append(timeContainer.get(ENGILSH_TIME_NO_SECOND));
			}

			if (has_time_way) {
				date.append(" " + timeContainer.get(ENGILSH_WAY));
			}
		}

		if (has_time_way) {
			dateFormat.append(" a");
		}

		if (timeContainer.containsKey(ENGILSH_ZONE)) {
			dateFormat.append(" z");
			date.append(" " + timeContainer.get(ENGILSH_ZONE));
		}

		return date.toString();
	}

	protected static int getFullYear(final int year) {
		int fyear = year;
		if (fyear < 1000) {
			if (fyear > 80) {
				fyear += 1900;
			} else {
				fyear += 2000;
			}
		}
		return fyear;
	}

	private static String getNowTime() {
		final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(new Date());
	}

	// 获取时间信息
	private static int getTimeNum(final String s) {
		int num = 0;
		if (s.length() == 1) {
			num = conversionCase(s.charAt(0));
		}
		if (s.length() == 2) {
			num = conversionCase(s.charAt(1));
			if (num == 10) {
				num = conversionCase(s.charAt(0)) * 10;
			} else {
				num = 10 + conversionCase(s.charAt(1));
			}

		}
		if (s.length() == 3) {
			num = conversionCase(s.charAt(0)) * 10
					+ conversionCase(s.charAt(2));
		}
		if (s.length() == 4) {
			num = conversionCase(s.charAt(0)) * 1000
					+ conversionCase(s.charAt(1)) * 100
					+ conversionCase(s.charAt(2)) * 10
					+ conversionCase(s.charAt(3));
		}
		return num;
	}

	/**
	 * 去除其他时候后尽量得到日期时间字符串
	 * 
	 * 基本算法：先找到一个有效的时间开始位置，然后找到可能的时间结束位置
	 * 
	 * @param time
	 * @return
	 */
	// public static String getTimeString(final String stime) {
	// if (null == stime) {
	// return "";
	// }
	// String time = stime;
	// if (time.indexOf("updated") != -1) {
	// time = stime.replace("updated", "");
	// if (time.indexOf("<") == -1 && time.indexOf(">") == -1) {
	// return time;
	// }
	// }
	//
	// final StringBuilder sb = new StringBuilder(time.length());
	//
	// boolean mark = false; // 是否具有日期/时间所必须具有的特殊字符
	//
	// final int n = time.length();
	// boolean haveTime = false;
	// for (int i = 0; i < n; i++) {
	// char c = time.charAt(i);
	//
	// if (isValidFirstTimeChar(c)) {
	// for (; i < n; i++) {
	// c = time.charAt(i);
	// if (!haveTime) {
	// haveTime = isTimeChar(c);
	// }
	//
	// // 时间字符串结束 一般时分秒后面有空格的就是非时间内容了
	// if (haveTime && isBlankChar(c)) {
	// break;
	// }
	//
	// // 到非时间字符串结束
	// if (!isValidTimeChar(c)) {
	// break;
	// }
	//
	// sb.append(c);
	//
	// if (isDateTimeMark(c) && validateTime(sb.toString())) {
	// mark = true;
	// }
	// }
	//
	// if (mark) {
	// break;
	// } else {
	// sb.setLength(0); // 没有特殊字符，这表示不可能是一个日期/时间字符串，重新寻找
	// }
	// }
	// }
	//
	// // 过滤掉标题中的假时间：2010年
	// // if (sb.toString().endsWith("年"))
	// // {
	// // sb.setLength(0);
	// // }
	// return sb.toString();
	//
	// }

	// protected static boolean isBlankChar(final char c) {
	// return NSFormatter.CHINESE_BLANK == c
	// || NSFormatter.CHINESE_BLANK1 == c
	// || NSFormatter.CHINESE_BLANK2 == c
	// || NSFormatter.CHINESE_BLANK3 == c
	// || NSFormatter.CHINESE_BLANK4 == c || ' ' == c;
	// }

	/**
	 * 判断给定的字符串是否符合时间格式
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDateTime(final String str) {
		boolean is = false;
		try {
			final Timestamp time = Timestamp.valueOf(str);
			final Timestamp after = Timestamp.valueOf("1990-01-01 00:00:00");
			// if (time.after(after) && time.before(new Date())) //
			// 这个业务判断由外面的业务逻辑代码来判断，本工具函数不能进行这个判断
			if (time.after(after)) {
				is = true;
			}
		} catch (final NumberFormatException nfe) {
			is = false;
		} catch (final Throwable e) {
			is = false;
		}
		return is;
	}

	private static boolean isDateTimeMark(final char c) {
		return -1 != "刚－/-:年月日时分秒".indexOf(c);
	}

	/**
	 * 判断是否为英文时间
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEnglishTime(final String stime) {
		String str = stime.trim().replaceAll(" +", " ");
		str = str.replace("-", " ");

		boolean isEnglish = false;
		boolean isTime = false;

		final String[] items = str.split(" ");
		if (items.length >= 2 && items.length <= 8) {
			for (int i = 0; i < items.length; i++) {
				final String item = items[i];
				if (Check.notEmpty(item)) {
					for (int j = 0; j < item.length(); j++) {
						final char c = item.charAt(j);
						if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z'
								|| c == ',') {
							if (i < items.length - 1) {
								isEnglish = true;
							}
						} else if (c == ':' || Character.isDigit(c)) {
							isTime = true;
						} else {
							return false;
						}
					}

				}

			}
		}
		return isEnglish && isTime;
	}

	/**
	 * 是否包含时区
	 * 
	 * @param time
	 * @return
	 */
	private static boolean isHaveTimeZone(final String time) {
		final String[] times = time.split(" ");
		final String end = times[times.length - 1];
		final char c = end.charAt(0);
		if (c >= 'a' && c <= 'z' && end.length() == 3) {
			return true;

		}
		return false;
	}

	// 判断是否为 long 型时间
	private static boolean isLongTime(final String time) {
		if (time.length() == 13) {
			try {
				Long.parseLong(time);
				return true;
			} catch (final NumberFormatException e) {
				return false;
			}

		}
		return false;
	}

	protected static boolean isMonth(final String str) {

		if (str.length() < 3) {
			return false;
		}
		final String start = str.substring(0, 3);
		if ("jan".equalsIgnoreCase(start) || "feb".equalsIgnoreCase(start)
				|| "mar".equalsIgnoreCase(start)
				|| "apr".equalsIgnoreCase(start)
				|| "may".equalsIgnoreCase(start)
				|| "jun".equalsIgnoreCase(start)
				|| "jul".equalsIgnoreCase(start)
				|| "aug".equalsIgnoreCase(start)
				|| "sep".equalsIgnoreCase(start)
				|| "oct".equalsIgnoreCase(start)
				|| "nov".equalsIgnoreCase(start)
				|| "dec".equalsIgnoreCase(start)) {
			return true;
		}
		return false;
	}

	protected static boolean isRightTime(final String str) {
		final Date d = DateUtil.parseDate(str);
		if (d != null) {
			if (d.getTime() > 0 && d.getTime() < System.currentTimeMillis()) {
				return true;
			}
		}
		return false;
	}

	protected static boolean isTimeChar(final char c) {
		return ':' == c;
	}

	/**
	 * 转换为Date型数据
	 * 
	 * @param time
	 * @param strict
	 *            严格验证，必须是日期时间字符串，不能包含其他 * @return
	 * @return
	 */
	// public static boolean isTimeString(final String time, final boolean
	// strict) {
	// if (Check.isEmpty(time)) {
	// return false;
	// }
	//
	// final int n = time.length();
	// // 英文时间 放行
	// if (isEnglishTime(time)) {
	// return true;
	// }
	// if (strict) {
	// for (int i = 0; i < n; i++) {
	// final char c = time.charAt(i);
	// if (!isValidTimeChar(c)) {
	// return false;
	// }
	// }
	// return true;
	// } else {
	// final Pattern date = Pattern.compile("\\d{4}.\\d{1,2}.\\d{1,2}");
	// final Matcher m = date.matcher(time);
	// if (m.find()) {
	// return true;
	// }
	// final String day = getTimeString(time);
	// return Check.notEmpty(day) && day.length() > 1;
	// }
	// }

	public static boolean isValidFirstTimeChar(final char c) {
		return Character.isDigit(c) || '昨' == c || '前' == c || 'u' == c
				|| '刚' == c;
	}

	public static boolean isValidTimeChar(final char c) {
		return '-' == c || '－' == c || '.' == c || '/' == c || ':' == c
				|| ' ' == c || Character.isDigit(c) || '年' == c || '月' == c
				|| '日' == c || '小' == c || '时' == c || '分' == c || '秒' == c
				|| '钟' == c || '前' == c || '今' == c || '昨' == c || '天' == c
				|| '刚' == c;
	}

	protected static boolean isWeek(final String str) {
		if (str.length() < 3) {
			return false;
		}
		final String start = str.substring(0, 3);
		if ("mon".equalsIgnoreCase(start) || "tue".equalsIgnoreCase(start)
				|| "wed".equalsIgnoreCase(start)
				|| "thu".equalsIgnoreCase(start)
				|| "fri".equalsIgnoreCase(start)
				|| "tue".equalsIgnoreCase(start)
				|| "sat".equalsIgnoreCase(start)) {
			return true;
		}
		return false;
	}

	public static Date parse(final String value) {
		if (Check.isEmpty(value)) {
			return null;
		}

		final int n = value.length();

		final String[] formats = { "yyyy-MM-dd HH:mm:ss.SSS",
				"yyyy/MM/dd HH:mm:ss.SSS", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
				"yyyy-M-d", "yyyy/MM/dd", "yyyy.MM.dd", "yyyyMMdd" };

		for (final String format : formats) {
			if (n == format.length()) {
				final Date time = parse(value, format);
				if (null != time) {
					return time;
				}
			}
		}

		return parseDateTime(value);
	}

	public static Date parse(final String value, final String format) {
		Date time = null;
		if (Check.notEmpty(value)) {
			try {
				time = (Date) (new SimpleDateFormat(format)).parseObject(value);
			} catch (final ParseException e) {
				return null; // 不需要抛出异常
			}
		}
		return time;
	}

	/**
	 * 解析日期
	 * 
	 * @param sDate
	 *            日期字符串;格式 yyyy-MM-dd 或yyyyMMdd
	 * @return Date
	 */
	public static Date parseDate(final String sDate) {
		Date date = parseDate(sDate, DEFAULT_DATE_FORMAT);
		if (null == date) {
			date = parseDate(sDate, "yyyyMMdd");
		}
		return date;
	}

	/**
	 * 解析日期
	 * 
	 * @param sDate
	 *            日期字符串
	 * @param format
	 *            格式
	 * @return Date
	 */
	public static Date parseDate(final String sDate, final String format) {
		Date date = null;
		if (Check.notEmpty(sDate)) {
			try {
				date = (Date) (new SimpleDateFormat(format)).parseObject(sDate);
			} catch (final ParseException e) {
				return date;
				// 不需要抛出异常
			}
		}
		return date;
	}

	// public static Date parseDateByText(final String time) {
	// return parseDateTime(time, "", "", new Date());
	// }

	/**
	 * 
	 * @param dateStr
	 * @return DEFAULT_DATE_FORMAT5
	 */
	public static Date[] parseDateForArr(final String dateStr) {
		Date date = null;
		date = parseDate(dateStr, DEFAULT_DATE_FORMAT5);
		if (null != date) {
			return new Date[] { date, date };
		} else if (null != parseDate(dateStr, DEFAULT_DATE_FORMAT6)) {
			date = parseDate(dateStr, DEFAULT_DATE_FORMAT6);

			return new Date[] { date,
					DateUtil.addDays(DateUtil.addMonths(date, 1), -1) };
		} else if (null != parseDate(dateStr, DEFAULT_DATE_FORMAT7)) {
			date = parseDate(dateStr, DEFAULT_DATE_FORMAT7);

			return new Date[] { date,
					DateUtil.addDays(DateUtil.addYears(date, 1), -1) };
		}

		return null;
	}

	protected static void parseDateFromEnlish(final String[] strs,
			final StringBuilder newtime, final String timeZone) {
		String time = "";
		String zone = timeZone;
		for (final String str : strs) {
			if (str.contains("-")) {
				final String[] days = str.split("-");
				if (days.length == 3) {
					if (days[2].length() <= 2 && days[0].length() <= 2) {
						final int year = Integer.parseInt(days[2]);
						newtime.append(getFullYear(year));
						newtime.append("-");
						newtime.append(days[0]);
						newtime.append("-");
						newtime.append(days[1]);
						newtime.append(" ");
					} else {
						newtime.append(str);
					}
				} else {
					newtime.append(str);
				}
				newtime.append(" ");
			} else if (str.contains(":")) {
				time = str;
			} else if (str.charAt(0) >= 'a' && str.charAt(0) <= 'z'
					&& str.length() == 3) {
				zone = str;
			}
		}
		newtime.append(time).append(" ");
		newtime.append(zone);
	}

	/**
	 * 解析 时间对象
	 * 
	 * @param time
	 * @param strict
	 * @return
	 */
	// public static DateInfo parseDateInfo(final String time, final boolean
	// strict) {
	// return parseDateInfo(time, strict, "", null);
	// }

	/**
	 * 解析 时间对象
	 * 
	 * @param time
	 * @param strict
	 * @param format
	 * @return
	 */
	// public static DateInfo parseDateInfo(final String time,
	// final boolean strict, final String format) {
	// return parseDateInfo(time, strict, format, new Date());
	// }
	//
	// public static DateInfo parseDateInfo(final String time,
	// final boolean strict, final String format, final Date baseTime) {
	// if (Check.isEmpty(time)) {
	// return new DateInfo();
	// }
	// final String strictTime = strict || isEnglishTime(time) ? time
	// : getTimeString(time);
	//
	// if (!isTimeString(strictTime, true)) {
	// return new DateInfo();
	// }
	//
	// return parseDateInfo(strictTime, "", format, baseTime);
	// }
	//
	// public static DateInfo parseDateInfo(final String time,
	// final String timeZone, final String format) {
	// return parseDateInfo(time, timeZone, format, new Date());
	// }

	/**
	 * 转换为Date型数据
	 * 
	 * @param time
	 * @return
	 */
	// public static DateInfo parseDateInfo(final String time,
	// final String timeZone, final String format, final Date baseTime) {
	// final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// final DateInfo info = new DateInfo();
	// if (Check.isEmpty(time)) {
	// return info;
	// }
	//
	// if (isLongTime(time)) {
	// final Date d = new Date(Long.parseLong(time));
	// info.setDate(d);
	// return info;
	// }
	// try {
	// // 需要把ZONE进行分析，如UTC+13这种没有可以用来表示的时区类型
	// final float addTime = parseTimeZone(timeZone);
	// String zone = "";
	// boolean isDefault = false;
	//
	// if (0 == addTime) {
	// isDefault = true;
	// zone = timeZone;
	// }
	//
	// String source = "";
	// boolean guess = false;
	// final Map<String, Boolean> dmap = format(time, zone, format,
	// baseTime);
	// for (final Map.Entry<String, Boolean> entry : dmap.entrySet()) {
	// source = entry.getKey();
	// guess = entry.getValue();
	// }
	//
	// Date date;
	// if (isHaveTimeZone(source)) {
	// date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
	// .parse(source);
	// } else {
	// date = df.parse(source);
	// }
	//
	// if (isDateTime(df.format(date))) {
	// date = parseZoneTime(date, addTime, isDefault);
	// info.setDate(date);
	// info.setGuess(guess);
	// return info;
	// }
	// } catch (final Throwable e) {
	// return info;
	// }
	// return info;
	// }

	/**
	 * 增加小时数，可以是正负值
	 * 
	 * @param date
	 *            Date
	 * @param nHours
	 *            小时
	 * @return Date
	 */
	public static Date parseDateTime(final Date date, final int nHours) {
		return DateUtil.parseDateTime(DateUtil.dateToString(date) + " "
				+ nHours + ":00:00");
	}

	/**
	 * 字符串转日期时间
	 * 
	 * @param sDateTime
	 *            日期时间字符串(DEFAULT_DATETIME_FORMAT)
	 * @return Date
	 */
	public static Date parseDateTime(final String sDateTime) {

		Date oDateTime = null;
		if ((null != sDateTime) && (0 < sDateTime.length())) {
			try {
				oDateTime = (Date) (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
						.parseObject(sDateTime);
			} catch (final ParseException e) {
				try {
					oDateTime = (Date) (new SimpleDateFormat("yyyyMMddHHmmss"))
							.parseObject(sDateTime);
				} catch (final ParseException e2) {
					try {
						oDateTime = (Date) (new SimpleDateFormat("HH:mm:ss"))
								.parseObject(sDateTime);
					} catch (final ParseException e3) {
						try {
							oDateTime = (Date) (new SimpleDateFormat("mm:ss"))
									.parseObject(sDateTime);
						} catch (final ParseException e4) {
							return parseDate(sDateTime);
						}
					}
				}
			}
		}
		return oDateTime;
	}

	// public static Date parseDateTime(final String time, final boolean strict)
	// {
	// return parseDateTime(time, strict, "");
	// }

	// public static Date parseDateTime(final String time, final boolean strict,
	// final String format) {
	// return parseDateTime(time, strict, format, new Date());
	// }

	/**
	 * 转换为Date型数据
	 * 
	 * @param time
	 * @param strict
	 *            严格验证，必须是日期时间字符串，不能包含其他
	 * @return
	 */
	// public static Date parseDateTime(final String time, final boolean strict,
	// final String format, final Date baseTime) {
	// return parseDateInfo(time, strict, format, baseTime).getDate();
	// }

	/**
	 * 转换为Date型数据
	 * 
	 * @param time
	 * @param strict
	 *            严格验证，必须是日期时间字符串，不能包含其他
	 * @return
	 */
	// public static Date parseDateTime(final String time, final boolean strict,
	// final String timeZone, final String format) {
	// if (Check.isEmpty(time)) {
	// return null;
	// }
	// final String strictTime = strict || isEnglishTime(time) ? time
	// : getTimeString(time);
	//
	// if (!isTimeString(strictTime, true)) {
	// return null;
	// }
	//
	// return parseDateTime(strictTime, timeZone, format, new Date());
	// }

	// public static Date parseDateTime(final String time, final Date baseTime)
	// {
	// return parseDateTime(time, true, "", baseTime);
	// }

	/**
	 * @param sDateTime
	 *            日期时间字符串
	 * @param sFormat
	 *            日期时间字符串格式
	 * @return Date
	 */
	public static Date parseDateTime(final String sDateTime,
			final String sFormat) {
		Date date = null;
		if ((null != sDateTime) && (0 <= sDateTime.length())) {
			try {
				date = (Date) (new SimpleDateFormat(sFormat))
						.parseObject(sDateTime);
			} catch (final ParseException e) {
				return date;
				// 不需要抛出异常
			}
		}
		return date;
	}

	// public static Date parseDateTime(final String time, final String
	// timeZone,
	// final String format) {
	// return parseDateInfo(time, timeZone, format, new Date()).getDate();
	// }
	//
	// public static Date parseDateTime(final String time, final String
	// timeZone,
	// final String format, final Date baseTime) {
	// return parseDateInfo(time, timeZone, format, baseTime).getDate();
	// }
	//
	// /**
	// * 从字符串中 提取时间
	// *
	// * @param time
	// * @return
	// */
	// public static Date parseDateTimeByText(final String time) {
	// return parseDateTime(time, new Date());
	// }
	//
	// /**
	// * 根据链接解析新闻时间
	// *
	// * @param url
	// * @return
	// */
	// public static Date parseDateTimeByURL(final String url) {
	// if (Check.isEmpty(url)) {
	// return null;
	// }
	// String strictTime = getTimeString(url);
	//
	// // FIXME 匹配各种时间格式
	// {
	// final Pattern date1 = Pattern.compile("(\\d{4}-\\d{1,2}-\\d{1,2})");
	// final Matcher m1 = date1.matcher(url);
	//
	// final Pattern date2 = Pattern.compile("(\\d{4}\\d{1,2}\\d{1,2})");
	// final Matcher m2 = date2.matcher(url);
	//
	// // final Pattern date =
	// // Pattern.compile("(\\d{4}.\\d{1,2}.\\d{1,2})");
	// // final Matcher m = date.matcher(time);
	//
	// if (m1.find()) {
	// strictTime = m1.group(1);
	// } else if (m2.find()) {
	// strictTime = m2.group(1);
	// return parseDate(strictTime, "yyyyMMdd");
	// // return parseDate(strictTime, "yyyyMMdd");
	// }
	// }
	//
	// if (!isTimeString(strictTime, true)) {
	// return null;
	// }
	//
	// return parseDateInfo(strictTime, "", "", new Date()).getDate();
	// }

	/**
	 * 分析短时间或日期字符串
	 * 
	 * 比如 13:12 或 3-21
	 * 
	 * @param timeOrDate
	 * @return
	 */
	// public static Date parseShortTimeOrDate(final String timeOrDate) {
	// if (Check.isEmpty(timeOrDate)) {
	// return null;
	// }
	//
	// final int pos = timeOrDate.indexOf(':');
	// if (-1 != pos) {
	// return parseDateTimeByText(DateUtil.dateToString(new Date()) + " "
	// + timeOrDate); // 日期默认为今天
	// }
	//
	// return parseDateTimeByText(timeOrDate);
	// }

	/**
	 * 解析时间
	 * 
	 * @param sTime
	 *            时间字符串;格式 HH:mm:ss
	 * @return Date
	 */
	public static Date parseTime(final String sTime) {
		Date time = null;
		if ((null != sTime) && (0 < sTime.length())) {
			try {
				time = (Date) (new SimpleDateFormat("HH:mm:ss"))
						.parseObject(sTime);
			} catch (final ParseException e) {
				try {
					time = (Date) (new SimpleDateFormat("HH:mm"))
							.parseObject(sTime);
				} catch (final ParseException e2) {
					try {
						time = (Date) (new SimpleDateFormat("HH"))
								.parseObject(sTime);
					} catch (final ParseException e3) {
						return time;
						// 不需要抛出异常
					}
				}
			}
		}
		return time;
	}

	/**
	 * 分析时间字符串是那种格式的
	 * 
	 * @param s
	 * @return
	 */
	protected static int parseTimeFormat(final String str) {
		int type = 0;
		Pattern p = null;
		Matcher m = null;
		if ("刚刚".equals(str)) {
			return 8;
		}
		if (str.contains("今天")) {
			return 7;
		}
		if (isEnglishTime(str)) {
			return 6;
		}
		if ("昨天".equals(str)) {
			return 5;
		}
		if ("前天".equals(str)) {
			return 3;
		}
		if ("半小时前".equals(str)) {
			return 4;
		}

		else {
			String patternStr = "\\d*[ 年]?";
			p = Pattern.compile(patternStr);
			m = p.matcher(str);
			if (m.find()) {
				if (str.length() < 7 && !str.contains(":")
						&& !str.contains("/") && !str.contains(QIAN)
						&& !str.contains("-") && !str.contains(YUE)) {
					return 2;
				}
			}
			patternStr = "天[\\s\\S]*?\\d{1,2}:\\d{1,2}";
			p = Pattern.compile(patternStr);
			m = p.matcher(str);
			if (m.find()) {
				type = 1;
			} else {
				patternStr = "^\\d{1,4}年\\d{1,2}月\\d{1,2}日";
				p = Pattern.compile(patternStr);
				m = p.matcher(str);
				if (m.find()) {
					return 0;
				}
			}
		}
		return type;
	}

	/**
	 * 字符串转日期时间[Timestamp类型]
	 * 
	 * @param sTimestamp
	 *            日期时间[包含毫秒的]字符串(DEFAULT_TIMESTAMP_FORMAT)
	 * @return 转换成Timestamp的后对象
	 */
	public static Timestamp parseTimestamp(final String sTimestamp) {
		return parseTimestamp(sTimestamp, DEFAULT_TIMESTAMP_FORMAT);
	}

	/**
	 * @param sTimestamp
	 *            日期时间[包含毫秒的]字符串
	 * @param sFormat
	 *            日期时间[包含毫秒的]字符串格式
	 * @return 转换成Timestamp后的对象
	 */
	public static Timestamp parseTimestamp(final String sTimestamp,
			final String sFormat) {
		Timestamp timestamp = null;
		if ((null != sTimestamp) && (0 < sTimestamp.trim().length())) {
			final Date oDate = parseDateTime(sTimestamp, sFormat); // 此处转换成的Date
																	// 包含了毫秒数

			timestamp = new java.sql.Timestamp(oDate.getTime());
		}

		return timestamp;
	}

	/**
	 * 解析时区
	 * 
	 * @param timeZone
	 * @return
	 */
	private static float parseTimeZone(final String timeZone) {

		if (timeZone.contains("+")) {
			return Float.parseFloat(timeZone.substring(
					timeZone.indexOf("+") + 1, timeZone.length()));
		}
		if (timeZone.contains("-")) {
			return -Float.parseFloat(timeZone.substring(
					timeZone.indexOf("-") + 1, timeZone.length()));
		}

		return 0;
	}

	/**
	 * 解析非三字母时区 我们将其配置成UTC+13 这种形式 的时区
	 * 
	 * @param date
	 * @param addTime
	 * @return
	 */
	private static Date parseZoneTime(final Date date, final float addTime,
			final boolean isDefault) {
		if (isDefault) {
			return date;
		}

		int addHour = 0;
		int addMin = 0;

		addHour = (int) (addTime / 1);
		addMin = (int) (addTime % 1 * 60);

		Date result = DateUtil.addHours(date, 8 - addHour);
		result = DateUtil.addMinutes(result, -addMin);

		return result;
	}

	/**
	 * 去掉没有用的时间信息
	 * 
	 * @param time
	 * @return
	 */
	private static String removeUnuseInfo(final String time) {

		String stime;
		stime = time.replace(" last updated", "");
		stime = stime.replace(" at", "");
		stime = stime.replace(" @", "");
		stime = stime.replace("bjt", "");
		stime = stime.replaceAll("[\\[\\]]", "");
		return stime;
	}

	/**
	 * 将时间字符串用空格区分日期和时间
	 * 
	 * @param s
	 * @return
	 */
	// private static String separateTimeAndDateBySpace(final String s) {
	// // String str = s.replace("　 ", " ");
	// String str = s;
	// str = str.replaceAll("&nbsp;", " ");
	// for (int i = 0; i < str.length(); i++) {
	// final char c = str.charAt(i);
	// if (isBlankChar(c)) {
	// str = str.replace(c, ' ');
	// }
	// }
	//
	// str = str.trim();
	// str = str.replaceAll("：", ":");
	// str = str.replace("(", " ");
	// str = str.replace(")", "");
	// str = str.replaceAll(",", " ");
	//
	// str = formatDayTime(str);
	//
	// String regex = "\\d+/(\\d+:\\d+)";
	// Pattern p = Pattern.compile(regex);
	// Matcher m = p.matcher(str);
	// if (m.find()) {
	// final String time = m.group(1);
	// final String start = s.substring(0, s.indexOf(time) - 1);
	// final String end = s.substring(s.indexOf(time));
	// str = start.concat(" ").concat(end);
	// }
	//
	// regex = "\\d+(-\\d+)?-\\d+\\s+\\d+(-\\d+)?-\\d+";
	// p = Pattern.compile(regex);
	// m = p.matcher(str);
	// if (m.find()) {
	// final String[] times = m.group().split("\\s+");
	// if (times.length == 2) {
	// final String day = times[0];
	// final String time = times[1];
	// str = day + " " + time.replaceAll("-", ":");
	// }
	//
	// }
	//
	// // str = str.trim().replaceAll("\\.0", "");
	// if (str.endsWith("\\.0")) {
	// str = str.substring(0, str.length() - 2);
	// }
	//
	// str = str.replace("|", "");
	// str = str.replaceAll("\\s+", " ");
	//
	// if (str.indexOf("日") != -1 && !str.contains(" ")) {
	// str = str.replace("日", "日 ");
	// }
	//
	// int index = str.indexOf("th");
	// if (index > 1 && index < str.length() - 4) {
	// if (str.charAt(index - 1) >= '0' && str.charAt(index - 1) <= '9'
	// && str.charAt(index + 2) != ' ') {
	// index += 2;
	// str = str.substring(0, index).concat(" ")
	// .concat(str.substring(index));
	// }
	//
	// }
	//
	// return str;
	// }

	private static boolean validateTime(final String time) {
		if (Check.isEmpty(time)) {
			return false;
		}
		if (time.trim().endsWith("年")) {
			return false;
		}
		if (time.length() <= 4) {
			for (int i = 0; i < time.length(); i++) {
				final char c = time.charAt(i);
				if (!Character.isDigit(c)) {
					return true;
				}
			}
			return false;
		}
		return true;
	}

	protected DateParser() {
	}
}
