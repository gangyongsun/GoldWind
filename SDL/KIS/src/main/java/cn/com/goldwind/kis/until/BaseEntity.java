package cn.com.goldwind.kis.until;

import java.io.Serializable;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分页页码,默认页码为1
	 */
	protected int page = 1;

	/**
	 * 分页每页数量,默认20条
	 */
	protected int size = 20;

	/**
	 * 排序列名称,默认为id
	 */
	protected String sidx = "id";

	/**
	 * 排序正序
	 */
	protected String sord = "asc";

}
