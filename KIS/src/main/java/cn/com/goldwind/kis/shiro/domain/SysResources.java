package cn.com.goldwind.kis.shiro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "sys_resources")
@Data
public class SysResources implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	/**
	 * 资源名称
	 */
	@Column(name = "resource_name")
	private String resourceName;

	/**
	 * 资源url
	 */
	@Column(name = "resource_url")
	private String resourceUrl;

	/**
	 * 资源类型 1:菜单 2：按钮
	 */
	@Column(name = "resource_type")
	private Integer resourceType;

	/**
	 * 父资源
	 */
	@Column(name = "parent_id")
	private Integer parentId;

	/**
	 * 排序
	 */
	@Column(name = "resource_sort")
	private Integer resourceSort;

}