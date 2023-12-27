package cn.com.goldwind.kis.bo;

import java.io.Serializable;

import lombok.Data;

@Data
public class SearchLogCountSum implements Serializable{

	private static final long serialVersionUID = -2812597013315808902L;
	
	private int noneCount;
	
	private int accurateCount;
	
	private int fuzzyCount;

}
