package cn.com.goldwind.kis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 用户点击查看术语详情记录类
 * 
 * @author alvin
 *
 */
@Data
@Table(name = "t_click_log")
public class ClickLog implements Serializable {
	private static final long serialVersionUID = -1632824561374104472L;

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
	 * 术语对应的条目主键id
	 */
	@Column(name = "termId")
	private Integer termId;

	/**
	 * 点击查看的次数
	 */
	@Column(name = "clickCounts")
	private Integer clickCounts;

	public ClickLog(String empNo, Integer termId) {
		super();
		this.empNo = empNo;
		this.termId = termId;
	}

	public ClickLog() {
		super();
	}

}
