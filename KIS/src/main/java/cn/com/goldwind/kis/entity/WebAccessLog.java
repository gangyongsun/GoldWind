package cn.com.goldwind.kis.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 网站访问记录日志类
 * 
 * @author alvin
 *
 */
@Data
@Table(name = "t_webaccess_log")
public class WebAccessLog implements Serializable {
	private static final long serialVersionUID = 8081147994272050710L;

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
	 * 访问时间
	 */
	@Column(name = "accessTime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date accessTime;

}
