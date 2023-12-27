package com.mdip.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FormatHandler {
	public static String isFormatIds(String s) {
		StringBuffer sb = new StringBuffer();
		if (!("".equals(s))) {
			if (s.contains(",")) {
				String[] arr = s.split(",");
				StringBuffer sb1 = new StringBuffer();

				for (int i = 0; i < arr.length; ++i) {
					arr[i] = arr[i].replaceAll("\\s*", "").trim();
					if (!("".equals(arr[i].trim()))) {
						sb1.append(arr[i]).append(",");
					}
				}
				String temp = "";
				if (!("".equals(sb1.toString()))) {
					temp = sb1.substring(0, sb1.length() - 1).toString();
				}
				if (temp.contains(",")) {
					String[] str = temp.split(",");
					for (int i = 0; i < str.length; ++i) {
						if (!("".equals(str[i]))) {
							sb.append("'").append(str[i]).append("',");
						}
					}
					if (!("".equals(sb)))
						return sb.substring(0, sb.length() - 1);
				} else {
					return "'" + temp + "'";
				}
			} else {
				return "'" + s + "'";
			}
		}
		return null;
	}

	public static String isSegmetDateOrString(String s, String name) {
		if ((s != null) && (!("".equals(s.trim()))) && (name != null) && (!("".equals(name.trim())))) {
			DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (s.contains(",")) {
				String[] str = s.split(",");
				if (str.length == 2) {
					String s1 = str[0].trim();
					String s2 = str[1].trim();
					if ((!("".equals(s1))) && (!("".equals(s2))))
						try {
							String str1 = format2.format(format1.parse(s1));
							String str2 = format2.format(format1.parse(s2));
							return "'" + str1 + "' <= " + name + "  and  " + name + " <= '" + str2 + "'";
						} catch (ParseException e) {
							return null;
						}
					if (("".equals(s1)) && (!("".equals(s2)))) {
						try {
							String sd = format2.format(format1.parse(s2));
							return name + " <= '" + sd + "'";
						} catch (ParseException sd) {
							return null;
						}
					}
					return null;
				}
				if (str.length == 1) {
					try {
						String date1 = format2.format(format1.parse(str[0].trim()));
						return "'" + date1 + "' <= " + name;
					} catch (ParseException e) {
						return null;
					}
				}
				return null;
			}
			return null;
		}
		return null;
	}

	public static void main(String[] arg) {
		StringBuffer ttemp = new StringBuffer();
		ttemp.append("dffffffffffffe");
		ttemp.delete(0, ttemp.length());
		System.out.println(isSegmetDateOrString("2017-01-03,", "o.name"));
	}
}
