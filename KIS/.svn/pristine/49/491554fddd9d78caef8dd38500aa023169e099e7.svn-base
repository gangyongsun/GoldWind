package cn.com.goldwind.kis.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * 术语操作类
 * 
 * @author alvin
 *
 */
@Data
@Table(name = "t_termoperate")
public class TermOperate {
	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	/**
	 * 术语ID
	 */
	@Column(name = "termId")
	private Integer termId;

	/**
	 * 用户OA号
	 */
	@Column(name = "empNO")
	private String empNO;

	/**
	 * 赞
	 */
	@Column(name = "praise")
	private boolean praise;

	/**
	 * 踩
	 */
	@Column(name = "trample")
	private boolean trample;

	/**
	 * 收藏
	 */
	@Column(name = "collect")
	private boolean collect;

	/**
	 * 评论
	 */
	@Column(name = "remark")
	private String remark;

}
