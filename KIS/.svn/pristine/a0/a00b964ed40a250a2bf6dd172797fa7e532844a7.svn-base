package cn.com.goldwind.kis.shiro.domain;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;

@Table(name = "sys_user_role")
@Data
public class SysUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
	private Integer id;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "role_id")
	private Integer roleId;

	public SysUserRole(Integer userId, Integer roleId) {
		super();
		this.userId = userId;
		this.roleId = roleId;
	}

}