package com.sojson.terminology.bo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 
 * @author alvin
 *
 */
@Data
public class Hit implements Serializable {
	private static final long serialVersionUID = 4773288418905595239L;

	public BigDecimal score;

	public int termbaseId;

	public int similarity;

	public int languageId;

	public String term;
	
	public EntryId entryId;
	
	public boolean collectStatus;

}
