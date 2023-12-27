package cn.com.goldwind.kis.mmseg.similarity;

import java.util.List;

/**
 * 搜索相似词获取接口
 * @author adolf  20191211
 * 1.返回值 （针对中英搜索是一致的）
 *       第一个词语为搜索词，证明命中了术语库中的词语，从第二个开始为相似词
 *       第一个词不是搜索词，证明该词条在术语库中，返回的列表都是相似词条
 * 2.中英混合搜索也支持，只是单纯计算中文的相似部分
 * 3.搜索为空值（即“”）返回为null,其他均返回List类型
 */
public class SimWordInterface {
	
	// 异步形式
	private static SimWordInterface instance = null;

	public synchronized static SimWordInterface getInstance(){
		if(instance == null){
			instance = new SimWordInterface();
		}
		return instance;
	}
	private  SimilarityWordsCalculation simi_generation = null;
	private SimWordInterface() {
		this.simi_generation = new SimilarityWordsCalculation();
	}

	public List<String> getSimilaryTopWords(String search_query) {
		if(search_query == null || search_query.equals("") == true) {
			return null;
		}
		return this.simi_generation.getSimilaryWords(search_query.toLowerCase());
	}
	
	
	public static void main(String[] args) {
		
		SimWordInterface sim_cal = SimWordInterface.getInstance();
		// 生成离线内容
		//sim_cal.getOfflineSimDict("/Users/adolf/eclipse-workspace/KeyInformationSystem/data/text_res.txt", 
		//						  "/Users/adolf/eclipse-workspace/KeyInformationSystem/data/sim_dict.txt");
		System.out.println("中文术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("电缆塔"));
		System.out.println("中文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("金风科技"));
		System.out.println(sim_cal.getSimilaryTopWords("机组动力电缆"));
		System.out.println("英文术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("Cable marker"));
		System.out.println("英文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWords("Permanent magnet wind turbine"));
		
		System.out.println(sim_cal.getSimilaryTopWords("双馈Generation"));
		System.out.println(sim_cal.getSimilaryTopWords(" "));

	}

}
