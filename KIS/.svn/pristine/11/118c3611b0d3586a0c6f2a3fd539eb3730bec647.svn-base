package cn.com.goldwind.kis.mmseg.similarity;

import java.util.List;

/**
 * 搜索相似词获取接口
 * <p>
 * 1.返回值 （针对中英搜索是一致的） 第一个词语为搜索词，证明命中了术语库中的词语，
 * <p>
 * 从第二个开始为相似词 第一个词不是搜索词，证明该词条在术语库中，返回的列表都是相似词条
 * <p>
 * 2.中英混合搜索也支持，只是单纯计算中文的相似部分 3.搜索为空值（即“”）返回为null,其他均返回List类型
 * 
 * @author adolf 20191211 ,updated at 2019-12-14 by yonggang
 */
public class SimWordInterface {
	private static SimWordInterface instance;

	public synchronized static SimWordInterface getInstance() {
		if (instance == null) {
			instance = new SimWordInterface();
		}
		return instance;
	}

	public List<String> getSimilaryTopWordsList(String conditionStr) {
		List<String> similaryWordsList = null;
		if (null != conditionStr && !"".equals(conditionStr)) {
			similaryWordsList = new SimilarityWordsCalculation().getSimilaryWords(conditionStr.toLowerCase());
		}
		return similaryWordsList;
	}

}
