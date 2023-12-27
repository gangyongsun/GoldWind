package cn.com.goldwind.kis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 精确/非精确匹配搜索记录
 * 
 * @author alvin
 *
 */
@Data
@Table(name = "t_search_log")
public class SearchLog implements Serializable {
	private static final long serialVersionUID = -2147451765408267207L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	/**
	 * 用户OA号
	 */
	@Column(name = "empNo")
	private String empNo;

	/**
	 * 搜索内容
	 */
	@Column(name = "findContent")
	private String findContent;

	/**
	 * 搜索的次数
	 */
	@Column(name = "searchCounts")
	private Integer searchCounts;

}
