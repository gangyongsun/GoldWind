package com.mdip.web.framework.base.controller;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mdip.common.util.DateUtil;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.entity.ParamsEntity;
import com.mdip.web.framework.base.entity.ResultEntity;
import com.mdip.web.framework.base.service.IUploadFileService;
import com.mdip.web.framework.base.util.BeanValidators;
import com.mdip.web.framework.base.util.IdGen;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {
	private static String jsonType = "contentTypejson";// 默认的视图解析器json
	public final String SEPERATOR = "/";// 目录分隔符
	public final String KETTLE_PROP_FILE = "kettle.properties";// kettle配置文件名字
	public final String KETTLE_WORKING_DIR_DESC = "#kettleWorkingDirectory";// kettle配置文件中指定的工作目录注释，用于修改配置文件中的工作目录
	public final String KETTLE_WORKING_DIR_KEY = "kettle.workingdir";// kettle配置文件中工作目录属性

	@Autowired
	protected Validator validator;// 验证Bean实例对象

	@Autowired
	protected IUploadFileService upfileService;

	/**
	 * 获取 HttpServletRequest
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}

	public ModelAndView getModelAndView() {
		return new ModelAndView();
	}

	public static String get32UUID() {
		return IdGen.uuid();
	}

	public PageEntity getPageEntity() {
		return new PageEntity();
	}

	public ParamsEntity getParamsEntity() {
		return new ParamsEntity(getRequest());
	}

	public static void logBefore(String interfaceName) {
		log.info("");
		log.info("start");
		log.info(interfaceName);
	}

	public static void logAfter() {
		log.info("end");
		log.info("");
	}

	/**
	 * 默认返回json格式
	 */
	protected String getJsonType() {
		return jsonType;
	}

	/**
	 * 服务端参数有效性验证
	 * 
	 * @param object
	 *            验证的实体对象
	 * @param groups
	 *            验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(ResultEntity info, Object object, Class<?>... groups) {
		try {
			BeanValidators.validateWithException(validator, object, groups);
		} catch (ConstraintViolationException ex) {
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			info.setStatus(2);
			addMessage(info, list.toArray(new String[] {}));
			return false;
		}
		return true;
	}

	/**
	 * 添加message消息
	 * 
	 * @param message
	 */
	protected void addMessage(ResultEntity info, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		info.addRmg(sb.toString());// 将校检错误信息返回
	}

	/**
	 * 服务端参数有效性验证
	 * 
	 * @param object
	 *            验证的实体对象
	 * @param groups
	 *            验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// setStatus(model, 0);
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtil.parseDate(text));
			}
			// @Override
			// public String getAsText() {
			// Object value = getValue();
			// return value != null ? DateUtils.formatDateTime((Date)value) :
			// "";
			// }
		});
	}

	protected void setValidatErrorMsg(BindingResult bind, ResultEntity info) {
		info.setStatus(2);
		List<ObjectError> oer = bind.getAllErrors();
		List<String> errorMessages = new ArrayList();
		List<FieldError> fer = bind.getFieldErrors();
		for (int i = 0; i < fer.size(); i++) {
			FieldError er = fer.get(i);

			if ("typeMismatch".equals(er.getCode()))// 暂时使用
			{
				// 类型转换应用，暂自己处理与分析
				errorMessages.add(er.getField() + ":" + getInputOrMsg(er.getDefaultMessage()));
			} else {
				errorMessages.add(er.getField() + ":" + er.getDefaultMessage());
			}
		}
		addMessage(info, errorMessages.toArray(new String[] {}));
	}

	private String getInputOrMsg(String message) {
		// String test = "Failed to convert property value of type
		// 'java.lang.String' to required type 'java.lang.Integer' for property
		// 'sort'; nested exception is java.lang.NumberFormatException: For
		// input string: ";
		// sort：必须为数字类型//required type 'java.lang.Integer' for property
		String em = "类型错误";
		try {
			int st = message.indexOf("required type '");
			int end = message.indexOf("' for property");
			String requiredtype = message.substring(st + 15, end);
			// System.out.println(requiredtype);
			switch (requiredtype) {
			case "java.lang.Integer":
				em = "类型必须是数字";
				break;
			case "java.lang.Boolean":
				em = "类型必须为布尔";
				break;
			case "java.lang.Double":
				em = "类型必须是数字";
				break;
			case "java.lang.Long":
				em = "类型必须是数字";
				break;
			case "java.lang.Float":
				em = "类型必须是数字";
				break;
			case "java.lang.Byte":
				em = "类型必须是数字";
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return em;
	}
}
