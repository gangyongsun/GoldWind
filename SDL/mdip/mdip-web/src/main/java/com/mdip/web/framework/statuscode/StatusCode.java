package com.mdip.web.framework.statuscode;

public class StatusCode {
	public static String getCodeInfo(int num) {
		String codemsg = "状态码不存在";
		switch (num) {
		case 0:
			codemsg = "正常";
			break;
		case 1:
			codemsg = "服务器内部错误";
			break;
		case 2:
			codemsg = "请求参数非法";
			break;
		case 3:
			codemsg = "权限校验失败";
			break;
		case 4:
			codemsg = "配额校验失败";
			break;
		case 5:
			codemsg = "ak不存在或者非法";
			break;
		case 101:
			codemsg = "服务禁用";
			break;
		case 102:
			codemsg = "不通过白名单或者安全码不对";
			break;
		case 202:
			codemsg = "无请求权限";
			break;
		case 203:
			codemsg = "无请求权限";
			break;
		case 204:
			codemsg = "无请求权限";
			break;
		case 205:
			codemsg = "无请求权限";
			break;
		case 210:
			codemsg = "权限资源不存在";
			break;
		case 233:
			codemsg = "无请求权限";
			break;
		case 231:
			codemsg = "用户uid，ak不存在";
			break;
		case 232:
			codemsg = "用户、ak被封禁";
			break;
		case 234:
			codemsg = "sn签名计算错误";
			break;
		case 235:
			codemsg = "用户名或密码错误";
			break;
		case 236:
			codemsg = "验证码错误";
			break;
		case 345:
			codemsg = "分钟配额超额";
			break;
		case 346:
			codemsg = "月配额超额";
			break;
		case 347:
			codemsg = "年配额超额";
			break;
		case 348:
			codemsg = "永久配额超额无请求权限";
			break;
		case 355:
			codemsg = "日配额超额";
			break;
		case 350:
			codemsg = "配额资源不存在";
		}
		return codemsg;
	}
}