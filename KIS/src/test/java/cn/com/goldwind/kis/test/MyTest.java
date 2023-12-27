package cn.com.goldwind.kis.test;

import org.junit.Test;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.mmseg.interfa.MMsegInterface;
import cn.com.goldwind.kis.mmseg.interfa.MMsegThread;
import cn.com.goldwind.kis.mmseg.similarity.LVdistance;
import cn.com.goldwind.kis.mmseg.similarity.SimWordInterface;
import cn.com.goldwind.kis.mmseg.similarity.VocabluaryTable;
import cn.com.goldwind.kis.utils.ProtoBuffUtil;

public class MyTest {
	@Test
	public void testSerialize() {
		AccessSummary as = new AccessSummary();
		as.setClassification("分类1");
		as.setTotalAccess(100);
		System.out.println("序列化");
		byte[] data = ProtoBuffUtil.serialize(as, AccessSummary.class);
		for (byte b : data) {
			System.out.print(b);
		}
		System.out.println();
		System.out.println("反序列化");
		AccessSummary as2 = ProtoBuffUtil.deSerialize(data, AccessSummary.class);
		System.out.println(as2);
	}

	@Test
	public void testSet() throws Exception {
		MMsegInterface testex = MMsegInterface.getInstance();
		String[] textList = { "变流器", "变桨系统", "鸡蛋", "金风风电场" };

		for (String text : textList) {
			System.out.println(testex.textWordSegToList(text));
		}
	}

	@Test
	public void newTestSplitWords() {
		SimWordInterface sim_cal = SimWordInterface.getInstance();
		// 生成离线内容
		// sim_cal.getOfflineSimDict("/Users/adolf/eclipse-workspace/KeyInformationSystem/data/text_res.txt",
		// "/Users/adolf/eclipse-workspace/KeyInformationSystem/data/sim_dict.txt");
		System.out.println("中文术语库中");
		System.out.println(sim_cal.getSimilaryTopWordsList("电缆塔"));
		System.out.println("中文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWordsList("幽闭恐惧症"));
		System.out.println("中文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWordsList("金风科技"));
		System.out.println(sim_cal.getSimilaryTopWordsList("机组动力电缆"));
		System.out.println("英文术语库中");
		System.out.println(sim_cal.getSimilaryTopWordsList("cable marker"));
		System.out.println("英文不在术语库中");
		System.out.println(sim_cal.getSimilaryTopWordsList("Permanent magnet wind turbine"));

		System.out.println(sim_cal.getSimilaryTopWordsList("双馈Generation"));
		System.out.println(sim_cal.getSimilaryTopWordsList("鸡蛋"));
	}

	/**
	 * 词汇表获取测试
	 */
	@Test
	public void testVocabluaryTable() {
		VocabluaryTable vocab_tab = VocabluaryTable.getInstance();
		System.out.println(vocab_tab.getChinCharWords("电缆塔"));
	}

	@Test
	public void testLVdistance() {
		// 要比较的两个字符串
		String str1 = "过去的一年，面对复杂多变的国际政治经济环境和艰巨繁重的国内改革发展任务，全国各族人民在中国共产党领导下，同心同德，团结奋进，改革开放和社会主义现代化建设取得新的重大成就。国内生产总值47.2万亿元，比上年增长9.2%；公共财政收入10.37万亿元，增长24.8%；粮食产量57121万吨，再创历史新高；城镇新增就业1221万人，城镇居民人均可支配收入和农村居民人均纯收入实际增长8.4%和11.4%。我们巩固和扩大了应对国际金融危机冲击成果，实现了“十二五”时期良好开局。";
		String str2 = "第十一届全国人民代表大会第一次会议以来的五年，是我国发展进程中极不平凡的五年。我们有效应对国际金融危机的严重冲击，保持经济平稳较快发展，国内生产总值从26.6万亿元增加到51.9万亿元，跃升到世界第二位；公共财政收入从5.1万亿元增加到11.7万亿元；累计新增城镇就业5870万人，城镇居民人均可支配收入和农村居民人均纯收入年均分别增长8.8%、9.9%；粮食产量实现“九连增”；重要领域改革取得新进展，开放型经济达到新水平；创新型国家建设取得新成就，载人航天、探月工程、载人深潜、北斗卫星导航系统、超级计算机、高速铁路等实现重大突破，第一艘航母“辽宁舰”入列；成功举办北京奥运会、残奥会和上海世博会；夺取抗击汶川特大地震、玉树强烈地震、舟曲特大山洪泥石流等严重自然灾害和灾后恢复重建重大胜利。我国社会生产力和综合国力显著提高，人民生活水平和社会保障水平显著提高，国际地位和国际影响力显著提高。我们圆满完成“十一五”规划，顺利实施“十二五”规划。社会主义经济建设、政治建设、文化建设、社会建设、生态文明建设取得重大进展，谱写了中国特色社会主义事业新篇章。";
		// String str1 = "今天心情相当不好，不要打搅我！";
		// String str2 = "今天心情相当不错，不要打断他！";
		System.out.print(LVdistance.levenshtein(str1, str2));
	}

	@Test
	public void testMmsegInterface1() {
		String content = "2016年1月17日中华全国总工会十六届四次执委会，选举农民工巨晓林为中华全国总工会副主席，他是我国第一位普通农民工当选全总副主席。巨晓林，男，汉族，中共党员，1962年9月出生，高中文化，家住陕西省岐山县祝家庄镇杜城村谢家坡组，1987年3月成为中国中铁(601390,股吧)电气化局一公司的农民工。巨晓林参加工作23年，先后参加了北同蒲线、鹰厦线、大秦线、京郑线、哈大线、迁曹线等十几条国家重点电气化铁路工程的施工。他创新施工方法43项，创造经济效益600多万元；他主编的《接触网施工经验和方法》，被配发给数千名接触网工作为工具书。北京市总工会授予他“知识型职工先进个人”，北京市政府授予他“北京市劳动模范”，中华全国总工会授予他“全国五一劳动奖章”。2015年4月，他被评为全国劳动模范和先进工作者。";
		String title = "测试程序";
		long starttime = System.currentTimeMillis();
		for (int i = 0; i < 10; i++) {
			MMsegThread sts = new MMsegThread(title, content);
			sts.start();
		}
		System.out.println(":::::::usetime=" + (System.currentTimeMillis() - starttime));
	}

	@Test
	public void testMmsegInterface2() {
		MMsegInterface testex = MMsegInterface.getInstance();
		String text = "机侧变流器";
		System.out.println(testex.textWordSegToList(text));
	}

	@Test
	public void testMMsegThread() {
		String content = "最近对长城汽车的车很关注，编程是数据科学的重要组成部分。在所有方面中，一般认为一个理解编程逻辑、循环、功能的大脑更有可能成为一个成功的数据科学家。那么，一个从来没有在学校或学院里学过编程项目的人呢？";
		String title = "测试程序";

		long starttime = System.currentTimeMillis();

		for (int i = 0; i < 10000; i++) {
			MMsegThread sts = new MMsegThread(title, content);
			sts.start();
		}
		System.out.println(":::::::usetime=" + (System.currentTimeMillis() - starttime));
	}

}
