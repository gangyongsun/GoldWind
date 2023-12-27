package com.mdip.web.framework.base.util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;

import com.google.common.collect.Lists;

public class StrUtil extends org.apache.commons.lang3.StringUtils {
	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	private static String SERVER_ID = "1";
	private static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '@', '#' };
	private static int idSequence = 10000;

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] getBytes(String str) {
		if (str != null) {
			try {
				return str.getBytes(CHARSET_NAME);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String checkStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	public static synchronized String generalSrid() {
		StringBuffer ret = new StringBuffer(20);
		ret.append(SERVER_ID);
		ret.append(getFormatDate("yyyyMMddHHmmss"));
		idSequence += 1;
		if (idSequence > 20000)
			idSequence -= 10000;
		ret.append(String.valueOf(idSequence).substring(1));

		return ret.toString();
	}

	public static String generalFileName(String srcFileName) {
		try {
			int index = srcFileName.lastIndexOf(".");
			return generalSrid() + srcFileName.substring(index).toLowerCase();
		} catch (Exception e) {
		}
		return generalSrid();
	}

	/**
	 * 转换为字节数组
	 * 
	 * @param str
	 * @return
	 */
	public static String toString(byte[] bytes) {
		try {
			return new String(bytes, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			return EMPTY;
		}
	}

	public static String parseOS(String agent) {
		String system = "Other";
		if (agent.indexOf("Windows NT 5.2") != -1)
			system = "Win2003";
		else if (agent.indexOf("Windows NT 5.1") != -1)
			system = "WinXP";
		else if (agent.indexOf("Windows NT 5.0") != -1)
			system = "Win2000";
		else if (agent.indexOf("Windows NT") != -1)
			system = "WinNT";
		else if (agent.indexOf("Windows 9") != -1)
			system = "Win9x";
		else if (agent.indexOf("unix") != -1)
			system = "unix";
		else if (agent.indexOf("SunOS") != -1)
			system = "SunOS";
		else if (agent.indexOf("BSD") != -1)
			system = "BSD";
		else if (agent.indexOf("linux") != -1)
			system = "linux";
		else if (agent.indexOf("Mac") != -1)
			system = "Mac";
		else
			system = "Other";
		return system;
	}

	public static String getFormatDate(String formatString) {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		String ret = sdf.format(now);
		return ret;
	}

	public static String getFormatDate(Date date, String formatString) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatString);
		String ret = sdf.format(date);
		return ret;
	}

	public static Date getCurrentDate() {
		Date now = new Date(System.currentTimeMillis());
		return now;
	}

	public static Date formatDate(String dateString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
		}
		return new Date();
	}

	public static Date formatDate(String dateString, String formatString) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatString);
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
		}
		return null;
	}

	public static Date formatSysDate() {
		String dateString = "";

		Calendar rightNow = Calendar.getInstance();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd hh mm ss");
		dateString = fmt.format(rightNow.getTime());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
		}
		return new Date();
	}

	public static int parseInt(String numberStr) {
		if (numberStr == null)
			return -1;
		Pattern pattern = Pattern.compile("^[\\-]{0,1}[0-9]+$");
		Matcher matcher = pattern.matcher(numberStr);
		if (matcher.find()) {
			return Integer.parseInt(numberStr);
		}
		return -1;
	}

	public static String toXMLString(String st) {
		if (st == null)
			st = "";
		st = st.replaceAll(">", "&gt;");
		st = st.replaceAll("<", "&lt;");
		st = st.replaceAll("\"", "&quot;");
		st = st.replaceAll("'", "&apos");
		st = st.replaceAll("&", "&amp;");
		return st;
	}

	public static String inHTML(int i) {
		if (i == '&')
			return "&amp;";
		else if (i == '<')
			return "&lt;";
		else if (i == '>')
			return "&gt;";
		else if (i == '"')
			return "&quot;";
		else
			return "" + (char) i;
	}

	public static String inHTML(String st) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < st.length(); i++) {
			buf.append(inHTML(st.charAt(i)));
		}
		return buf.toString();
	}

	public static String getJsString(CharSequence jsStr) {
		if (jsStr == null)
			jsStr = "";
		StringBuffer sb = new StringBuffer("");
		char c = '\000';
		for (int i = 0; i < jsStr.length(); i++) {
			char b = c;
			c = jsStr.charAt(i);
			switch (c) {
			case '"':
			case '\\':
				sb.append('\\');
				sb.append(c);
				break;
			case '/':
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\r':
				sb.append("\\r");
				break;
			default:
				if ((c < ' ') || ((c >= '') && (c < ' ')) || ((c >= ' ') && (c < '℀'))) {
					String t = "000" + Integer.toHexString(c);
					sb.append("\\u" + t.substring(t.length() - 4));
				} else {
					sb.append(c);
				}
			}

		}

		return sb.toString();
	}

	public static Object dealNull(Object obj) {
		Object returnstr = null;
		if (obj == null)
			returnstr = "";
		else {
			returnstr = obj;
		}
		return returnstr;
	}

	public static String escape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (int i = 0; i < src.length(); i++) {
			char j = src.charAt(i);
			if ((Character.isDigit(j)) || (Character.isLowerCase(j)) || (Character.isUpperCase(j))) {
				tmp.append(j);
			} else if (j < 'Ā') {
				tmp.append("%");
				if (j < '\020')
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		int pos = 0;

		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else if (pos == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				tmp.append(src.substring(lastPos, pos));
				lastPos = pos;
			}
		}

		return tmp.toString();
	}

	public static String sqlParser(String sqlstr) {
		long t = System.currentTimeMillis();
		List indexs = new ArrayList();
		Matcher matcher = Pattern.compile("(select )|(from )").matcher(sqlstr);
		while (matcher.find()) {
			String s = "0000000000" + matcher.start();
			s = s.substring(s.length() - 10);
			indexs.add(s + matcher.group().charAt(0));
		}
		Object[] sortIndexs = indexs.toArray();
		Arrays.sort(sortIndexs);
		indexs.clear();
		for (int i = 0; i < sortIndexs.length; i++) {
			indexs.add(sortIndexs[i]);
		}
		System.out.println(indexs);
		while (indexs.size() > 2) {
			List delIndex = new ArrayList();
			for (int i = 1; i < indexs.size() - 1; i++) {
				if ((indexs.get(i).toString().endsWith("s")) && (indexs.get(i + 1).toString().endsWith("f"))) {
					delIndex.add(indexs.get(i));
					delIndex.add(indexs.get(i + 1));
				}
			}
			if (delIndex.isEmpty())
				break;
			indexs.removeAll(delIndex);
			delIndex.clear();
		}
		System.out.print(indexs);
		int index = Integer.parseInt(indexs.get(1).toString().replaceAll("f", ""));
		sqlstr = sqlstr.substring(index);
		System.out.println("sqlstr=select count(*) " + sqlstr);
		System.out.println("时间： " + (System.currentTimeMillis() - t));
		return "";
	}

	private static String toUnsignedString(long i, int shift) {
		char[] buf = new char[64];
		int charPos = 64;
		int radix = 1 << shift;
		long mask = radix - 1;
		do {
			charPos--;
			buf[charPos] = digits[(int) (i & mask)];
			i >>>= shift;
		} while (i != 0L);
		return new String(buf, charPos, 64 - charPos);
	}

	public static String escape2(String str) {
		if (str == null) {
			return "";
		}
		byte[] data = str.getBytes();
		int len = data.length;
		StringBuffer result = new StringBuffer(len * 2);

		int begin = 0, count = 0, tt = 0;
		for (int i = 0; i < len; i++) {
			switch ((char) data[i]) {
			case '&':
				result.append(new String(data, begin, count));
				result.append("&amp;");
				begin = i + 1;
				count = 0;
				break;
			case '\"':
				result.append(new String(data, begin, count));
				result.append("&quot;");
				begin = i + 1;
				count = 0;
				break;
			case '<':
				result.append(new String(data, begin, count));
				result.append("&lt;");
				begin = i + 1;
				count = 0;
				break;
			case '>':
				result.append(new String(data, begin, count));
				result.append("&gt;");
				begin = i + 1;
				count = 0;
				break;
			case '\n':
				result.append(new String(data, begin, count));
				result.append("<br>");
				begin = i + 1;
				count = 0;
				break;
			case '$':
				result.append(new String(data, begin, count));
				result.append("$$");
				begin = i + 1;
				count = 0;
				break;
			default:
				count++;
				break;
			}
		}
		if (count > 0) {
			result.append(new String(data, begin, count));
		}
		return result.toString();

	}

	/**
	 * 替换掉HTML标签方法
	 */
	public static String replaceHtml(String html) {

		if (isBlank(html)) {

			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param html
	 * @return
	 */
	public static String replaceMobileHtml(String html) {
		if (html == null) {
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	/**
	 * 替换为手机识别的HTML，去掉样式及属性，保留回车。
	 * 
	 * @param txt
	 * @return
	 */
	public static String toHtml(String txt) {
		if (txt == null) {
			return "";
		}
		return replace(replace(Encodes.escapeHtml(txt), "\n", "<br/>"), "\t", "&nbsp; &nbsp; ");
	}

	/**
	 * 缩略字符串（不区分中英文字符）
	 * 
	 * @param str
	 *            目标字符串
	 * @param length
	 *            截取长度
	 * @return
	 */
	public static String abbr(String str, int length) {
		if (str == null) {
			return "";
		}
		try {
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3) {
					sb.append(c);
				} else {
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String abbr2(String param, int length) {
		if (param == null) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; // 是不是HTML代码
		boolean isHTML = false; // 是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			try {
				if (!isCode && !isHTML) {
					n += String.valueOf(temp).getBytes("GBK").length;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			if (n <= length - 3) {
				result.append(temp);
			} else {
				result.append("...");
				break;
			}
		}
		// 取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		// 去掉不需要结素标记的HTML标记
		temp_result = temp_result.replaceAll(
				"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
				"");
		// 去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		// 用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = Lists.newArrayList();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		// 补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	/**
	 * 转换为Double类型
	 */
	public static Double toDouble(Object val) {
		if (val == null) {
			return 0D;
		}
		try {
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e) {
			return 0D;
		}
	}

	/**
	 * 转换为Float类型
	 */
	public static Float toFloat(Object val) {
		return toDouble(val).floatValue();
	}

	/**
	 * 转换为Long类型
	 */
	public static Long toLong(Object val) {
		return toDouble(val).longValue();
	}

	/**
	 * 转换为Integer类型
	 */
	public static Integer toInteger(Object val) {
		return toLong(val).intValue();
	}

	/**
	 * 获得用户远程地址
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader("X-Real-IP");
		if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("X-Forwarded-For");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("Proxy-Client-IP");
		} else if (isNotBlank(remoteAddr)) {
			remoteAddr = request.getHeader("WL-Proxy-Client-IP");
		}
		return remoteAddr != null ? remoteAddr : request.getRemoteAddr();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 * 驼峰命名法工具
	 * 
	 * @return toCamelCase("hello_world") == "helloWorld"
	 *         toCapitalizeCamelCase("hello_world") == "HelloWorld"
	 *         toUnderScoreCase("helloWorld") = "hello_world"
	 */
	public static String toUnderScoreCase(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/**
	 * 如果不为空，则设置值
	 * 
	 * @param target
	 * @param source
	 */
	public static void setValueIfNotBlank(String target, String source) {
		if (isNotBlank(source)) {
			target = source;
		}
	}

	/**
	 * 转换为JS获取对象值，生成三目运算返回结果
	 * 
	 * @param objectString
	 *            对象串 例如：row.user.id
	 *            返回：!row?'':!row.user?'':!row.user.id?'':row.user.id
	 */
	public static String jsGetVal(String objectString) {
		StringBuilder result = new StringBuilder();
		StringBuilder val = new StringBuilder();
		String[] vals = split(objectString, ".");
		for (int i = 0; i < vals.length; i++) {
			val.append("." + vals[i]);
			result.append("!" + (val.substring(1)) + "?'':");
		}
		result.append(val.substring(1));
		return result.toString();
	}

	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
	}

}