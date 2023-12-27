package cn.com.goldwind.kis.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 按类型浏览记录
 * 
 * @author alvin
 *
 */
@Data
@Table(name = "t_termtypebrowse_log")
public class TermTypeBrowseLog implements Serializable {
	private static final long serialVersionUID = 450811778324320733L;

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
	 * 术语类型
	 */
	@Column(name = "termType")
	private String termType;

	/**
	 * 浏览次数
	 */
	@Column(name = "browseCounts")
	private Integer browseCounts;

	public TermTypeBrowseLog(String empNo, String termType) {
		super();
		this.empNo = empNo;
		this.termType = termType;
	}

	public TermTypeBrowseLog() {
		super();
	}

}
