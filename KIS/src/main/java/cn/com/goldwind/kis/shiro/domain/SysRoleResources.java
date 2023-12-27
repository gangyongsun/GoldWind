package cn.com.goldwind.kis.shiro.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Table(name = "sys_role_resources")
@Data
public class SysRoleResources implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "resources_id")
	private Integer resourcesId;

	public SysRoleResources(Integer roleId, Integer resourcesId) {
		super();
		this.roleId = roleId;
		this.resourcesId = resourcesId;
	}

}