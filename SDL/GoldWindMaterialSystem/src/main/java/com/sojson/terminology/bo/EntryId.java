package com.sojson.terminology.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author alvin
 *
 */
@Data
public class EntryId implements Serializable {
	private static final long serialVersionUID = -3893514727789734108L;

	public int id;

	public String uuid;
}
