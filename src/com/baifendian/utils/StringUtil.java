

package com.baifendian.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具
 * 
 * @author caviler
 * @author ice
 */
public final class StringUtil {

	/**
	 * ASCII表中除空格外的可见字符与对应的全角字符的相对偏移
	 */
	static final int CONVERT_STEP = 65248; // 全角半角转换间隔 65281-33或者65374-126

	/**
	 * 半角对应于ASCII表中可见字符到~结束，偏移位值为126(Decimal)
	 */
	static final char DBC_CHAR_END = 126; // 半角~

	/**
	 * 半角对应于ASCII表中可见字符从!开始，偏移位值为33(Decimal)
	 */
	static final char DBC_CHAR_START = 33; // 半角!

	/**
	 * 半角空格的值，在ASCII中为32(Decimal)
	 */
	static final char DBC_SPACE = ' '; // 半角空格

	// ------------------------------------------------------------------------

	/**
	 * 全角对应于ASCII表的可见字符到～结束，偏移值为65374
	 */
	static final char SBC_CHAR_END = 65374; // 全角～

	/**
	 * 全角对应于ASCII表的可见字符从！开始，偏移值为65281
	 */
	static final char SBC_CHAR_START = 65281; // 全角！

	// ------------------------------------------------------------------------

	/**
	 * 全角空格的值，它没有遵从与ASCII的相对偏移，必须单独处理
	 */
	static final char SBC_SPACE = 12288; // 全角空格 12288

	/**
	 * 半角字符 ===> 全角字符转换 只处理半角的空格，半角的!到半角的&tilde;;之间的字符，忽略其他
	 * 
	 * @param src
	 *            要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String DBC2SBC(final String src) {
		if (src == null) {
			return src;
		}
		final StringBuilder buf = new StringBuilder(src.length());
		final char[] ca = src.toCharArray();
		for (final char element : ca) {
			if (element == DBC_SPACE) { // 如果是半角空格，直接用全角空格替代
				buf.append(SBC_SPACE);
			} else if ((element >= DBC_CHAR_START) && (element <= DBC_CHAR_END)) { // 字符是!到~之间的可见字符
				buf.append((char) (element + CONVERT_STEP));
			} else { // 不对全角空格以及ascii表中其他可见字符之外的字符做任何处理
				buf.append(element);
			}
		}
		return buf.toString();
	}

	/**
	 * 数字字符串中,以全角半角逗号、顿号、空格隔开的多个重复的变为一个split,全角数字转为半角
	 * 
	 * @param s
	 *            源字符串
	 * @param split
	 *            定义的分隔符号
	 * @return 格式化的分隔符号
	 */
	public static String formatIntArrayString(final String s, final char split) {
		if (Check.isEmpty(s)) {
			return s;
		}
		final StringBuilder buf = new StringBuilder(s.length());
		final char[] ca = s.toCharArray();

		int j = 0; // 记录重复分隔符
		int k = 0; // 保证去掉开头的分隔符

		boolean b = false; // 是否在规定分隔符范围内（全角半角逗号、空格、顿号）
		for (final char c : ca) {
			b = (c == DBC_SPACE || c == ',' || c == '，' || c == '、' || c == SBC_SPACE);
			if (k > 0 && 0 == j && b) {
				buf.append(split); // 重复分隔符变为一个
				j++;
			} else if (!b) {
				// 正常保存分隔符范围以外的字符
				if ('０' <= c && c <= '９') {
					buf.append((char) (c - CONVERT_STEP)); // 全角数字转为半角
				} else {
					buf.append(c);
				}
				j = 0;
				k++;
			}
		}
		if (j > 0) {
			return buf.substring(0, buf.length() - 1).toString(); // 如果末尾有多余分隔符则去掉
		}
		return buf.toString();
	}

	// /**
	// * 转义双引号, " 转义为 &quot; , 方便把双引号显示在页面上 ,quotation
	// marks(qm):引号,translate:翻译.
	// *
	// * @param str
	// * 要处理的字符串
	// * @return 转义完成的串
	// */
	// public static String translate_qm(final String str)
	// {
	// if (null == str || ("").equals(str))
	// {
	// return str;
	// }
	// String newName = str;
	// Pattern p;
	// Matcher m;
	// p = Pattern.compile("\""); // 双引号
	// m = p.matcher(newName);
	// newName = m.replaceAll("&quot;");
	// return newName;
	// }
	//
	// /**
	// * 转义尖括号, <> 转义为 〈〉 ,point bracket:尖括号 ,translate:翻译. //＜＞《〈〉》
	// *
	// * @param str
	// * 要处理的字符串
	// * @return 转义完成的串
	// */
	// public static String translate_pb(final String str)
	// {
	// if (null == str || ("").equals(str))
	// {
	// return str;
	// }
	// String newName = str;
	// Pattern p;
	// Matcher m;
	// p = Pattern.compile("<"); //
	// m = p.matcher(newName);
	// newName = m.replaceAll("〈");
	// p = Pattern.compile(">"); //
	// m = p.matcher(newName);
	// newName = m.replaceAll("〉");
	// return newName;
	// }
	//
	// /**
	// * 转义, & 转义为 &amp;
	// *
	// * @param str
	// * 要处理的字符串
	// * @return 转义完成的串
	// */
	// public static String translate_and(final String str)
	// {
	//
	// if (null == str || ("").equals(str))
	// {
	// return str;
	// }
	// String newName = str;
	// Pattern p;
	// Matcher m;
	// p = Pattern.compile("&"); // 双引号
	// m = p.matcher(newName);
	// newName = m.replaceAll("&amp;");
	// return newName;
	// }

	/**
	 * 判断字符串的长度是否超出限定长度：若超出，则按限定长度来截取
	 * 
	 * @param sText
	 *            需要判断比较的字符串
	 * @param length
	 *            限制的长度
	 * @return 最终返回的字符串
	 */
	public static String getStringByLength(final String sText, final int length) {
		if (Check.isEmpty(sText)) {
			return null;
		}
		return sText.length() > length ? sText.substring(0, length) : sText;
	}

	/**
	 * 判断字符串的长度是否超出限定长度：若超出，则按限定长度来截取,中间省略
	 * 
	 * @param sText
	 * @param length
	 * @return
	 */
	public static String getStringByLength1(final String sText, final int length) {
		if (Check.isEmpty(sText)) {
			return null;
		}
		final int mid = length / 2;

		return sText.length() > length ? sText.substring(0, mid) + "…"
				+ sText.substring(sText.length() - mid, sText.length()) : sText;

	}

	/**
	 * 返回 UTF-8 编码后的字符串字节长度
	 * 
	 * @param sText
	 *            要处理的字符串
	 * @param nWidth
	 *            用于屏幕显示的最大宽度
	 * @return 截取后的字符串
	 */
	public static String getStringByUTF8BytesLength(final String sText,
			final int nWidth) {
		final int n = ((null == sText) ? 0 : sText.length());
		int i = 0;
		int w = 0;
		for (; w < nWidth && i < n; i++) {
			final int c = sText.charAt(i);
			if (c < 255) {
				w++;
			} else {
				// 一个汉字占用 3 个字节
				w++;
				w++;
				w++;
			}
		}
		return w < nWidth ? sText : sText.substring(0, (w > nWidth ? --i : i)); // 处理半个汉字的情况
	}

	/**
	 * 返回 UTF-8 编码后的字符串字节长度<br/>
	 * 中文字符为2字节
	 * 
	 * @param sText
	 *            要处理的字符串
	 * @return 转换后的字符串长度
	 */
	public static int getStringLength(final String sText) {
		final int n = ((null == sText) ? 0 : sText.length());
		int i = 0;
		int w = 0;
		for (; i < n; i++) {
			final int c = sText.charAt(i);
			if (c < 255) {
				w++;
			} else {
				// 一个汉字占用 2 个字节
				w++;
				w++;
			}
		}
		return w;
	}

	/**
	 * 计算 UTF-8 格式的字符串长度
	 * 
	 * @param s
	 *            要计算的字符串
	 * @return 此字符串的长度
	 */
	public static int getStringUTF8BytesLenght(final String s) {
		try {
			return (null == s) ? 0 : s.getBytes("UTF-8").length;
		} catch (final UnsupportedEncodingException e) {
			return 0;
		}
	}

	public static int indexOf(final String keywords, final String symbol) {
		return StringUtil.indexOf(keywords, new String[] { symbol }, 0, false);
	}

	public static int indexOf(final String keywords, final String[] symbols,
			final int start, final boolean escape) {
		return StringUtil.indexOf(keywords, symbols, start, escape ? '\\'
				: null);
	}

	public static int indexOf(final String keywords, final String[] symbols,
			final int start, final Character escape) {
		if (Check.notEmpty(keywords) && Check.notEmpty(symbols)) {
			int i;
			for (final String symbol : symbols) {
				i = keywords.indexOf(symbol, start);
				while (-1 != i) {
					if (null == escape) {
						return i;
					} else if ((start == i)
							|| (escape != keywords.charAt(i - 1))) {
						return i;
					}
					i = keywords.indexOf(symbol, i + 1);
				}
			}
		}
		return -1;
	}

	/**
	 * 校验是否包含中文
	 */
	public static boolean isContainChinese(final String str) {
		final String patrn = "[\u4E00-\u9FA5]";
		final Pattern p = Pattern.compile(patrn); // 判断是否包含英文字母

		// 创建 Matcher 对象
		final Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 校验是否包含英文字母
	 */
	public static boolean isContainEN(final String str) {
		final String patrn = "[a-zA-Z]";
		final Pattern p = Pattern.compile(patrn); // 判断是否包含英文字母

		// 创建 Matcher 对象
		final Matcher m = p.matcher(str);
		return m.find();
	}

	/**
	 * 判断字符串长度是否超出最大值<br>
	 * 中文字符为2字节
	 * 
	 * @param sText
	 *            要处理的字符串
	 * @param max
	 *            最大长度
	 * @return true 大于 false 小于
	 */
	public static boolean isOutOfLength(final String sText, final int max) {
		return getStringLength(sText) > max;
	}

	/**
	 * 判断邮件格式是否合法
	 * 
	 * @param sEmail
	 *            要处理的字符串
	 * @return true:合法,false:不合法
	 */
	public static boolean isValidEmail(final String sEmail) {
		if (null == sEmail || sEmail.trim().length() == 0) {
			return false;
		}
		// \w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*
		// final String regEx =
		// "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		final String regEx = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		final Pattern p = Pattern.compile(regEx);
		final Matcher m = p.matcher(sEmail);

		return m.find();
	}

	/**
	 * 判断用户名格式是否合法
	 * 
	 * @param sNick
	 *            用户账号
	 * @return true:合法,false:不合法
	 */
	public static boolean isValidNick(final String sNick) {
		if (null == sNick || sNick.trim().length() == 0) {
			return false;
		}

		final String regEx = "^[0-9a-zA-Z]{3,20}$";

		final Pattern p = Pattern.compile(regEx);
		final Matcher m = p.matcher(sNick);

		return m.find();
	}

	static boolean isWhitespaceChar(final char c) {
		final char[] wc = new char[] { '\t', '\n', ' ', '\f', '\r' };
		for (final char cr : wc) {
			if (cr == c) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 字符串拼接函数(split 的反函数)
	 * 
	 * @param array
	 *            整型数组
	 * @param sDelimiter
	 *            分隔符
	 * @return 以sDelimiter为分隔符的字符串
	 */
	public static String join(final int[] array, final String sDelimiter) {
		if ((array == null) || (array.length == 0)) {
			return null;
		}

		final StringBuilder stringbuffer = new StringBuilder();

		for (final int s : array) {
			stringbuffer.append(s);
			stringbuffer.append(sDelimiter);
		}
		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		return stringbuffer.toString();
	}

	/**
	 * 字符串拼接函数(split 的反函数)
	 * 
	 * @param array
	 *            整型数组
	 * @param sDelimiter
	 *            分隔符
	 * @return 以sDelimiter为分隔符的字符串
	 */
	public static String join(final long[] array, final String sDelimiter) {
		if ((array == null) || (array.length == 0)) {
			return null;
		}

		final StringBuilder stringbuffer = new StringBuilder();

		for (final long s : array) {
			stringbuffer.append(s);
			stringbuffer.append(sDelimiter);
		}
		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		return stringbuffer.toString();
	}

	/**
	 * 字符串拼接函数(split 的反函数)
	 * 
	 * @param array
	 *            String型数组
	 * @param sDelimiter
	 *            分隔字符串
	 * @return 以sDelimiter字符串为分隔的字符串
	 */
	public static String join(final String[] array, final String sDelimiter) {
		if ((array == null) || (array.length == 0)) {
			return null;
		}

		final StringBuilder stringbuffer = new StringBuilder();

		for (final String s : array) {
			stringbuffer.append(s);
			stringbuffer.append(sDelimiter);
		}
		stringbuffer.deleteCharAt(stringbuffer.length() - 1);
		return stringbuffer.toString();
	}

	public static int lastIndexOf(final String keywords,
			final String[] symbols, final int start, final Character escape) {
		if (Check.notEmpty(keywords) && Check.notEmpty(symbols)) {
			int i;
			for (final String symbol : symbols) {
				i = keywords.lastIndexOf(symbol, start);
				while (-1 != i) {
					if (null == escape) {
						return i;
					} else if ((i == 0) || (escape != keywords.charAt(i - 1))) {
						return i;
					}
					i = keywords.lastIndexOf(symbol, i - 1);
				}
			}
		}
		return -1;
	}

	public static String replace(final String sourceStr, final String regex,
			final String replacement) {
		{
			String str = sourceStr;
			final Pattern p = Pattern.compile(regex);
			final Matcher m = p.matcher(str);
			int n = 0;
			final List<Integer> list = new ArrayList<Integer>();
			while (m.find(n)) {
				n = m.start();
				list.add(n);
				n = n + 1;
			}
			for (int i = list.size() - 1; i >= 0; i--) {
				str = str.substring(0, list.get(i)) + replacement
						+ str.substring(list.get(i) + 1, str.length());
			}
			return str;
		}
	}

	/**
	 * 替代String字段replaceAll，这个方法不知为何总有问题
	 * 
	 * @param srcStr
	 *            原始字符串
	 * @param regx
	 *            被替换的子串
	 * @param replacement
	 *            替换成的子串
	 * @return
	 */
	public static String replaceAll(final String srcStr, final String regx,
			final String replacement) {
		final StringBuilder sb = new StringBuilder();
		int i = 0;
		int j = 0;
		j = srcStr.indexOf(regx, i);
		while (-1 != j) {
			sb.append(srcStr.subSequence(i, j));
			sb.append(replacement);
			i = j + regx.length();
			if (i >= srcStr.length()) {
				break;
			}
			j = srcStr.indexOf(regx, i);
		}
		if (i < srcStr.length()) {
			sb.append(srcStr.substring(i, srcStr.length()));
		}
		return sb.toString();
	}

	/**
	 * 字符串反转
	 * 
	 * @param s
	 * @return
	 */
	public static String reverse(final String s) {
		if (null == s) {
			return null;
		}

		char c;
		final char[] a = s.toCharArray();
		final int n = a.length / 2;
		int i = 0;
		int k = a.length - 1;
		for (; i < n; i++, k--) {
			c = a[i];
			a[i] = a[k];
			a[k] = c;
		}

		return new String(a);
	}

	/**
	 * <PRE>
	 *  全角字符 ===&gt; 半角字符转换 
	 * 只处理全角的空格，全角！到全角～之间的字符，忽略其他
	 * 
	 * </PRE>
	 * 
	 * @param src
	 *            要处做转换处理的字符串
	 * @return 转换处理后的字符串
	 */
	public static String SBC2DBC(final String src) {
		if (src == null) {
			return src;
		}
		final StringBuilder buf = new StringBuilder(src.length());
		final char[] ca = src.toCharArray();
		for (int i = 0; i < src.length(); i++) {
			if (ca[i] >= SBC_CHAR_START && ca[i] <= SBC_CHAR_END) { // 如果位于全角！到全角～区间内
				buf.append((char) (ca[i] - CONVERT_STEP));
			} else if (ca[i] == SBC_SPACE) { // 如果是全角空格
				buf.append(DBC_SPACE);
			} else { // 不处理全角空格，全角！到全角～区间外的字符
				buf.append(ca[i]);
			}
		}
		return buf.toString();
	}

	public static CharSequence tidyWhitespaceChar(final CharSequence s) {
		final StringBuilder sb = new StringBuilder();
		for (final char c : s.toString().toCharArray()) {
			if (!isWhitespaceChar(c)) {
				sb.append(c);
			}
		}

		return sb;
	}

	/**
	 * 去掉字符串左右的半角空格,全角空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理完成的字符串
	 */
	public static String trim(final String str) {
		if (null == str || 0 == str.length()) {
			return str;
		}
		return str.replaceAll("(^[ |　|\\x3F]*|[ |　|\\x3F]*$)", "");
	}

	/**
	 * 去掉字符串中所有的半角空格,全角空格(包括中间位置的空格)
	 * 
	 * @param str
	 *            要处理的字符串
	 * @return 处理完成的字符串
	 */
	public static String trimAll(final String str) {
		if (null == str) {
			return str;
		}
		String newName = str;
		Pattern p;
		Matcher m;
		p = Pattern.compile(" "); // 去半角空格
		m = p.matcher(newName);
		newName = m.replaceAll("");
		p = Pattern.compile("　"); // 去全角空格
		m = p.matcher(newName);
		newName = m.replaceAll("");
		return newName;
	}

	private StringUtil() {
	}
}
