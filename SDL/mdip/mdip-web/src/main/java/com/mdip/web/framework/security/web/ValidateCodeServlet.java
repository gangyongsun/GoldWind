package com.mdip.web.framework.security.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

/**
 * 生成随机验证码
 * 
 */
@SuppressWarnings("serial")
public class ValidateCodeServlet extends HttpServlet {

	public static final String VALIDATE_CODE = "validateCode";
	private int w = 70;
	private int h = 26;

	public ValidateCodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public static boolean validate(HttpServletRequest request, String validateCode) {
		String code = (String) request.getSession().getAttribute(VALIDATE_CODE);
		return validateCode.toUpperCase().equals(code);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String validateCode = request.getParameter(VALIDATE_CODE); // AJAX验证，成功返回true
		if (StringUtils.isNotBlank(validateCode)) {
			response.getOutputStream().print(validate(request, validateCode) ? "true" : "false");
		} else {
			this.doPost(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createImage(request, response);
	}

	private void createImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("content-type", "text/html;charset=UTF-8");
		/*
		 * 生成字符
		 */
		String s = createCharacter();
		request.getSession().setAttribute(VALIDATE_CODE, s);

		OutputStream out = response.getOutputStream();
		out.write(s.getBytes());

	}

	public static String createCharacter() {
		char[] codeSeq = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };
		String[] fontTypes = { "Arial", "Arial Black", "AvantGarde Bk BT", "Calibri" };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);// random.nextInt(10));

			s.append(r);
		}
		return s.toString();
	}

}