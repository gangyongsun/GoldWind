package cn.com.goldwind.kis.dto;

import lombok.Data;

/**
 * 关键词
 * 
 * @author alvin
 *
 */
@Data
public class KeyInfoDto {

	/**
	 * 主键
	 */
	private String id;
	/**
	 * 关键词中文名
	 */
	private String chinese;

	/**
	 * 关键词英文名
	 */
	private String english;

	/**
	 * 关键词格式
	 */
	private String form;

	/**
	 * 图片地址链接
	 */
	private String image;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 三种分类机制
	 */
	private String subject;
	private String function;
	private String position;

}
