package com.mdip.web.framework.config;

import java.io.IOException;
import java.util.Properties;

public class Config {
	/*********************************************************************
	 * 静态常量
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";// 显示/隐藏
	public static final String YES = "1";
	public static final String NO = "0"; // 是/否
	public static final String TRUE = "true";
	public static final String FALSE = "false";// 对/错
	/*********************************************************************
	 * 
	 * /*********************************************************************
	 * 系统常态
	 */
	/**
	 * 项目相对路径 发布后的默认上传保存地址
	 */
	public static String uploadPath = "upload";// 发布后的默认上传保存地址
	/**
	 * 项目相对路径 当前系统保留
	 */
	public static String uploadImage = "uploadImage";// 当前系统保留
	/**
	 * 项目相对路径 重定向默认地址
	 */
	public static String ewebeditUploadPath = "tempfile";// 重定向默认地址
	public static String defaultEncodeing = System.getProperty("file.encoding");
	/**
	 * 编码类型
	 */
	public static String defaultTemplateEncoding = "UTF-8";

	/**
	 * 主要站点，默认为""
	 */
	public static String mainSite = "";
	/**
	 * 服务器ID
	 */
	public static String serveId = "1";
	/**
	 * 项目发布的路径（绝对路径）
	 */
	public static String webRoot = "";
	/**
	 * 项目名称
	 */
	public static String appName = "mdip-web";
	/**
	 * 系统上传默认使用此地址 重定向默认地址 默认为D:\\temp ，安装时根据config.properties配置修改
	 */
	public static String redirectPath = "D:\\temp";// 重定向默认地址
	/**
	 * 系统静态资源
	 */
	public static String staticFile = ".css,.js,.png,.jpg,.gif,.jpeg,.bmp,.ico,.swf,.psd,.htc,.crx,.xpi,.exe,.ipa,.apk,.html,.htm";
	/**
	 * 系统上传要求最大值
	 */
	public static long maxUploadSize = 10 * 1024 * 1024;
	/**
	 * 第一次安装初始化管理员的账号名，数据库生成以 id=“admin”的name=“lantrack”记录
	 */
	public static String demouser = "lantrack";// 系统初根用户不可删除
	/**
	 * 第一次安装软件时admin使用的临时密码
	 */
	public static String password = "202CB962AC59075B964B07152D234B70";// 系统初根用户不可删除
	/**
	 * 试用版可访问的方式
	 */
	public static String rserverIp = "AA05E4EE9BB3034387E331BCDC9A13AC,ABDD7F719A7A6230922E156719FA55DB";
	/**
	 * 是否允许用户同设备同时登录,
	 */
	public static String usermultiAccountLogin = "true";
	/**
	 * 动态映射URL后缀
	 */
	public static String suffix = ".html";
	/**
	 * 前缀
	 */
	public static String prefix = "/";
	/*********************************************************************
	 * 
	 * /*********************************************************************
	 * redis settings
	 */
	public static String redisKeyPrefix = "lantrack";
	public static String redisHost = "127.0.0.1";
	public static String redisPort = "6379";
	// *********************************************************************

	static {
		try {
			Properties props = new Properties();
			props.load(Config.class.getResourceAsStream("../../../../../config.properties"));
			// 系统常量
			if (props.containsKey("mainSite")) {
				mainSite = props.getProperty("mainSite");
			}
			if (props.containsKey("serveId")) {
				serveId = props.getProperty("serveId");
			}
			if (props.containsKey("webRoot")) {
				webRoot = props.getProperty("webRoot");
			}
			if (props.containsKey("appName")) {
				appName = props.getProperty("appName");
			}
			if (props.containsKey("redirectPath")) {
				redirectPath = props.getProperty("redirectPath");
			}
			if (props.containsKey("staticFile")) {
				staticFile = props.getProperty("staticFile");
			}
			if (props.containsKey("maxUploadSize")) {
				maxUploadSize = Long.parseLong(props.getProperty("maxUploadSize"));
			}
			if (props.containsKey("usermultiAccountLogin")) {
				usermultiAccountLogin = props.getProperty("usermultiAccountLogin");
			}

			// spring 视图前缀与后缀
			if (props.containsKey("suffix")) {
				suffix = props.getProperty("suffix");
			}
			if (props.containsKey("prefix")) {
				prefix = props.getProperty("prefix");
			}

			// 演示账号
			if (props.containsKey("demouser")) {
				demouser = props.getProperty("demouser");
			}
			if (props.containsKey("password")) {
				password = props.getProperty("password");
			}
			if (props.containsKey("rserverIp")) {
				rserverIp = props.getProperty("rserverIp");
			}
			// redis配置相关信息
			if (props.containsKey("redisKeyPrefix")) {
				redisKeyPrefix = props.getProperty("redisKeyPrefix");
			}
			if (props.containsKey("redisHost")) {
				redisHost = props.getProperty("redisHost");
			}
			if (props.containsKey("redisPort")) {
				redisPort = props.getProperty("redisPort");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
