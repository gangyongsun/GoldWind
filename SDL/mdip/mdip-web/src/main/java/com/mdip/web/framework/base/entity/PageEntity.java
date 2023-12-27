package com.mdip.web.framework.base.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 页面分页实体类
 * 
 * @author yonggang
 *
 */
public class PageEntity extends ResultEntity {
	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private int currentPage;// 当前页，默认1
	@Setter
	@Getter
	private int pageSize;// 页面条数，默认20
	@Setter
	@Getter
	private int totalPage;// 总页数
	@Setter
	@Getter
	private int totalRecord;// 总条数
	@Setter
	@Getter
	private String orderBy = "";

	public PageEntity() {
		super();
		this.currentPage = Integer.valueOf(1);
		this.pageSize = Integer.valueOf(20);
	}

}