package cn.com.goldwind.kis.mmseg.similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.com.goldwind.kis.utils.IoUtil;

/**
 * 本地离线计算和线上实施计算的入口
 * 
 * @author adolf 20191205
 *
 */
public class SimilarityWordsCalculation {
	private VocabluaryTable vocabluaryTable;
	private static Set<String> chineseVocabluary = null;
	private static Set<String> englishVocabluary = null;

	public SimilarityWordsCalculation() {
		if (vocabluaryTable == null) {
			vocabluaryTable = VocabluaryTable.getInstance();
		}
		chineseVocabluary = vocabluaryTable.getChinCharVocab();
		englishVocabluary = vocabluaryTable.getEnglishCharVocab();
	}

	/**
	 * 获取离线的相似词典
	 * 
	 * @param fileName
	 * @param outfile
	 * @param language  语言类型 1:中文 ：2:英文
	 * @return
	 */
	public boolean getOfflineSimDict(String fileName, String outfile, int language) {
		if (fileName.equals("") == true || outfile.equals("") == true) {
			return false;
		}
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		FileOutputStream fos = null;
		OutputStreamWriter os = null;
		BufferedWriter bw = null;
		try {
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, "UTF-8");
			br = new BufferedReader(isr);
			String textLine = "";
			Set<String> tmp_item_dict = new HashSet<String>();
			List<String> arr_item = new ArrayList<String>();
			while ((textLine = br.readLine()) != null) {
				if (textLine.length() > 1) {
					String arrays[] = textLine.split(";");
					for (int i = 0; i < arrays.length; i++) {
						arr_item.add(arrays[i].toLowerCase().trim());
						tmp_item_dict.add(arrays[i].toLowerCase().trim());
					}
				}
			}

			fos = new FileOutputStream(new File(outfile));
			os = new OutputStreamWriter(fos, "UTF-8");
			bw = new BufferedWriter(os);
			if(1 == language) {
				for (int idx = 0; idx < arr_item.size(); idx++) {
					String current_item = arr_item.get(idx);
					bw.write(current_item + "\t");
					Map<String, Float> ret_arrs = this.getTermTopRankScore(current_item, tmp_item_dict, 10);
					
					for (String key : ret_arrs.keySet()) {
						if (ret_arrs.get(key) > 0.25) {
							bw.write(key + ":" + ret_arrs.get(key) + ";");
						}
					}
					bw.write("\n");
				}
				System.out.println("chinese dict size is " + tmp_item_dict.size());

			}
			else if( 2 == language) {
				for (int idx = 0; idx < arr_item.size(); idx++) {
					String current_item = arr_item.get(idx);
					bw.write(current_item + "\t");
					Map<String,Float> ret_arrs = this.getTermTopRankScoreEN(current_item,tmp_item_dict,10);
					for (String key : ret_arrs.keySet()) {
						if (ret_arrs.get(key) > 0.1) {
							bw.write(key + ":" + ret_arrs.get(key) + ";");
						}
					}
					bw.write("\n");
				}
				System.out.println("english dict size is " + tmp_item_dict.size());

			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IoUtil.close(br, isr, fis, bw, os, fos);
		}

		return true;
	}

	/**
	 * 返回分数最高的前size个分词结果和对应的rank
	 * 
	 * @param content
	 * @param size
	 * @return
	 */
	public Map<String, Float> getTermTopRankScore(String content, Set<String> tmp_dict, int size) {
		Map<String, Float> score_map = new HashMap<String, Float>();
		for (String item : tmp_dict) {
			if (content.equals(item) == false) {
				// 线上要匹配分词快
				float sim_score = (float) LVdistance.levenshtein(content, item);
				score_map.put(item, sim_score);
			}
		}

		Map<String, Float> result = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size,
				new Comparator<Map.Entry<String, Float>>() {
					@Override
					public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				}).addAll(score_map.entrySet()).toList()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * 线上实时计算版本 返回分数最高的前size个结果
	 * 
	 * @param content
	 * @param size
	 * @return
	 */
	public Map<String, Float> getRealTimeTopItem(String content, int size) {
		Map<String, Float> score_map = new HashMap<String, Float>();

		for (String item : chineseVocabluary) {
			// 线上要匹配分词快,数据量少暂时使用该方法
			float sim_score = (float) LVdistance.levenshtein(content, item);
			score_map.put(item, sim_score);
		}
		if (score_map == null || score_map.size() == 0) {
			return null;
		}
		Map<String, Float> result = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size,
				new Comparator<Map.Entry<String, Float>>() {
					@Override
					public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				}).addAll(score_map.entrySet()).toList()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * 返回分数最高的结果，英文版本 使用的算法较重，可以用于文本相似度计算
	 * 离线版本
	 * @param content
	 * @param size
	 * @return
	 */
	public Map<String, Float> getTermTopRankScoreEN(String content, Set<String> tmp_dict, int size) {
		Map<String, Float> score_map = new HashMap<String, Float>();

		//if(content.indexOf(" ") != -1 ) {
			// 多个英文词 的搜索情况
			for (String item : tmp_dict) {
				// 去掉自身
				if (content.equals(item) == false) {
					// 线上要匹配分词快, 离线完全英文不需要考虑单个词语的情况
					float sim_score = (float) this.getDocumentSimilary(content, item);
					score_map.put(item, sim_score);
				}
			}
		/*} else { 
			// 单个英文词，或者混合输入的情况,不按着单词匹配没有意义 
			for(String item: tmp_dict) {
				if(content.equals(item) == false) { // 线上要匹配分词快 
					float sim_score = (float)LVdistance.levenshtein(content,item); 
					score_map.put(item, sim_score);
				} 
			} 
		}
		*/

		Map<String, Float> result = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size,
				new Comparator<Map.Entry<String, Float>>() {
					@Override
					public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				}).addAll(score_map.entrySet()).toList()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	/**
	 * 返回分数最高的结果，英文版本 实施计算版本
	 * 线上版本
	 * @param content
	 * @param size
	 * @return
	 */
	public Map<String, Float> getRealTimeTopItemEN(String content, int size) {
		Map<String, Float> score_map = new HashMap<String, Float>();
		if(content.indexOf(" ") != -1 ) {
		// 多个英文词 的搜索情况
			for (String item : englishVocabluary) {
				float sim_score = (float) this.getDocumentSimilary(content, item);
				score_map.put(item, sim_score);
			}
		} else { 
			// 单个英文词，或者混合输入的情况 
			for(String item: englishVocabluary) {
				if(content.equals(item) == false) { // 线上要匹配分词快 
					float sim_score = (float)LVdistance.levenshtein(content,item); 
					score_map.put(item, sim_score);
				} 
			} 
		}
		if (score_map == null || score_map.size() == 0) {
			return null;
		}
		Map<String, Float> result = new LinkedHashMap<String, Float>();
		for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size,
				new Comparator<Map.Entry<String, Float>>() {
					@Override
					public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
						return o1.getValue().compareTo(o2.getValue());
					}
				}).addAll(score_map.entrySet()).toList()) {
			result.put(entry.getKey(), entry.getValue());
		}

		return result;
	}

	// 英文短语进行单词切分
	public Map<String, Float> splitEnglishContentToWords(String content) {
		if (content.equals("") == true) {
			return null;
		}
		Map<String, Float> ret_value = new HashMap<String, Float>();
		// 只做空格切分和tab鉴，词语判断
		String words_arr[] = content.split(" ");
		for (int i = 0; i < words_arr.length; i++) {
			if (words_arr.length > 0) {
				if (ret_value.containsKey(words_arr[i]) == true) {
					float old_val = ret_value.get(words_arr[i]);
					ret_value.put(words_arr[i], (1.0f + old_val));
				} else {
					ret_value.put(words_arr[i], 1.0f);
				}
			}
		}

		return ret_value;
	}

	/**
	 * 采用雅可比算法进行计算，同时进行加权计算
	 * 
	 * @param curDocu
	 * @param document
	 * @return
	 */
	public double getDocumentSimilary(String curDocu, String document) {
		if (curDocu.equals("") == true || document.equals("") == true) {
			return 0.0;
		}
		Map<String, Float> cur_value = this.splitEnglishContentToWords(curDocu);
		Map<String, Float> dou_value = this.splitEnglishContentToWords(document);
		float cur_length = curDocu.length();
		float doc_length = document.length();
		float max_length = 0.0f;
		if (cur_length > doc_length) {
			max_length = cur_length;
		} else {
			max_length = doc_length;
		}

		if (cur_value != null && dou_value != null) {

			return JaccardDistance.jaccard(cur_value, dou_value, max_length);
		} else {
			return 0.0;
		}
	}

	public List<String> getSimilaryWords(String search_word) {
		if (search_word == null || search_word.length() < 1) {
			return null;
		}
		List<String> ret_value = new ArrayList<String>();
		// 非中文处理
		if (TextUtility.isAllNonChinese(search_word.getBytes()) == true) {
			ret_value = vocabluaryTable.getEngCharWords(search_word);
			if (ret_value != null) {
				return ret_value;
			} else {
				Map<String, Float> ret_arr = this.getRealTimeTopItemEN(search_word, 10);
				if (ret_arr == null || ret_arr.size() == 0) {
					return ret_value;
				}
				List<String> value = new ArrayList<String>();
				for (String key : ret_arr.keySet()) {
					if (ret_arr.get(key) > 0.1) {
						value.add(key);
					}
					// System.out.println("keey==value\t" + key + "==" + ret_arr.get(key));
				}
				return value;
			}
		} else {
			// 中文
			ret_value = vocabluaryTable.getChinCharWords(search_word);
			if (ret_value != null) {
				return ret_value;
			} else {
				Map<String, Float> ret_arr = this.getRealTimeTopItem(search_word, 10);
				if (ret_arr == null || ret_arr.size() == 0) {
					return ret_value;
				}
				List<String> value = new ArrayList<String>();
				for (String key : ret_arr.keySet()) {
					if (ret_arr.get(key) > 0.1) {
						value.add(key);
					}
					// System.out.println("keey==value\t" + key + "==" + ret_arr.get(key));
				}
				return value;
			}
		}
	}
	
}
