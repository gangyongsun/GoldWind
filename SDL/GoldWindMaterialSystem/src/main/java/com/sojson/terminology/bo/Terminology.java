package com.sojson.terminology.bo;

import lombok.Data;

/**
 * 关键词实体类
 * 
 * @author alvin
 *
 */
@Data
public class Terminology {
	/**
	 * EntryID
	 */
	public String entryId;

	/**
	 * Entry类别
	 */
	public String entryClass;

	/**
	 * 关键词名
	 */
	public String termName;

	/**
	 * 关键词定义
	 */
	public String termDefination;
	/**
	 * 关键词定义来源
	 */
	public String termDefinationSource;

	/**
	 * 关键词例句
	 */
	public String termExampleSentence;

	/**
	 * 关键词例句来源
	 */
	public String termExampleSentenceSource;

	/**
	 * 所在的关键词库
	 */
	public String termbaseId;

	/**
	 * 关键词语言
	 */
	public String languageId;

}
