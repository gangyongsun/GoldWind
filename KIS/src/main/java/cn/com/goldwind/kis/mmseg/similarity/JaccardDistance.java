package cn.com.goldwind.kis.mmseg.similarity;

import java.util.Map;

public class JaccardDistance {

	/**
	 * 采用雅可比算法进行计算，同时进行加权计算
	 * 
	 * @param curValueMap
	 * @param douValueMap
	 * @param maxLength
	 * @return
	 */
	public static double jaccard(Map<String, Float> curValueMap, Map<String, Float> douValueMap, float maxLength) {
		double result = 0.0;
		if (null != curValueMap && null != douValueMap) {
			float simScore = 0.0f;
			for (String curKey : curValueMap.keySet()) {
				if (douValueMap.containsKey(curKey)) {
					simScore = simScore + douValueMap.get(curKey) * curKey.length();
				}
			}
			result = simScore / maxLength;
		}
		return result;
	}

}
