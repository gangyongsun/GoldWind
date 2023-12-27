package cn.com.goldwind.kis.mmseg.similarity;

import java.util.Map;

public class JaccardDistance {
	
	/**
	 * 采用雅可比算法进行计算，同时进行加权计算
	 * @param cur_value
	 * @param cur_value
     * @return
     */
	public static double jaccard(Map<String, Float> cur_value, Map<String, Float> dou_value,float max_length) {
		
		if(cur_value !=null && dou_value != null) {
			
			float sim_score = 0.0f;
			for(String cur_key:cur_value.keySet()) {
				
				if(dou_value.containsKey(cur_key) == true) {
					sim_score = sim_score + dou_value.get(cur_key)*cur_key.length();
				}
			}
			return sim_score / max_length;
		}
		else {
			return 0.0;
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
