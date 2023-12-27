package com.sojson.common.model;

import java.io.Serializable;

import lombok.Data;
//import net.sf.json.JSONObject;
import lombok.NoArgsConstructor;

/**
 * 用户角色关系对应
 * 
 * @author alvin
 *
 */
@Data
@NoArgsConstructor
public class UUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * {@link UUser.id}
	 */
	private Long uid;

	/**
	 * {@link URole.id}
	 */
	private Long rid;

	public UUserRole(Long uid, Long rid) {
		this.uid = uid;
		this.rid = rid;
	}

}