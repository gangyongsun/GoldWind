package cn.com.goldwind.kis.shiro.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cn.com.goldwind.kis.utils.FileUtil;
import cn.com.goldwind.kis.utils.MathUtil;

/**
 * super controller
 * 
 * @author alvin
 *
 */
public class BaseController {
	protected final static Class<? extends Object> SELF = FileUtil.class;

	protected Map<String, Object> resultMap = new LinkedHashMap<String, Object>();

	public static String URL404 = "/404.html";

	public ModelAndView redirect(String redirectUrl, Map<String, Object>... parament) {
		ModelAndView view = new ModelAndView(new RedirectView(redirectUrl));
		if (null != parament && parament.length > 0) {
			view.addAllObjects(parament[0]);
		}
		return view;
	}

	public ModelAndView redirect404() {
		return new ModelAndView(new RedirectView(URL404));
	}

	/**
	 * 跳转
	 * 
	 * @param response
	 * @param url
	 */
	protected void gotoURL(HttpServletResponse response, String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证用户OA号
	 * 
	 * @param userId 用户OA号
	 * @param key    OA系统加密后的校验文本
	 * @return
	 */
	protected boolean validateUserId(String userId, String key) {
		boolean flag = false;
		String md5 = MathUtil.getMD5(userId + "@" + "@#$%^KIS@@@@@)(");
		if (md5.equals(key)) {
			flag = true;
		}
		return flag;
	}

}
