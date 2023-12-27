package cn.com.goldwind.kis.mmseg.similarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import cn.com.goldwind.kis.mmseg.encrypt.EncryptDictionary;
import cn.com.goldwind.kis.mmseg.main.Dictionary;
import cn.com.goldwind.kis.utils.IoUtil;
import cn.com.goldwind.kis.utils.PropertiesUtil;

/**
 * 线上词表加载与存储
 * 
 * @author adolf 201911205,updated at 2019-12-14 by yonggang
 *
 */
public class VocabluaryTable {
	private static final Logger log = Logger.getLogger(VocabluaryTable.class.getName());
	/**
	 * 中英对应的相似词向量
	 */
	private static Map<String, Vector<String>> words_sim_arr_ch = new HashMap<String, Vector<String>>();;
	private static Map<String, Vector<String>> words_sim_arr_en = new HashMap<String, Vector<String>>();;

	private static VocabluaryTable instance = null;

	public synchronized static VocabluaryTable getInstance() {
		if (instance == null) {
			instance = new VocabluaryTable();
			instance.loadResource();
		}
		return instance;
	}
	public static String toUtf8(String str) { 
        try {
			return new String(str.getBytes("UTF-8"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "";
	}
	
	private void loadResource() {
		long start_time = System.currentTimeMillis(); 
		String wordsPath = "";
		// only for local test，剔除关联，方便本地测试
		/*Properties props = System.getProperties();
		String system_name = props.getProperty("os.name");
		if(system_name.equals("Mac OS X") == true) {
			String defPath ="";
			URL url = Dictionary.class.getClassLoader().getResource("data_entity");
			
			if (url != null) {
				defPath = url.getFile();
				log.info("look up in classpath=" + defPath);
			} else {
				defPath = System.getProperty("user.dir") + "/data_entity";
				
				log.info("look up in user.dir=" + defPath);
			}
			//wordsPath = new File(defPath);
			//System.out.println("load default file name \t" + defPath);
			wordsPath = defPath;
		}
		else {
			// online 
			wordsPath = new String(PropertiesUtil.getValueByKey("SERVER_DICT_PATH", "config.properties"));
		}*/
		wordsPath = new String(PropertiesUtil.getValueByKey("SERVER_DICT_PATH", "config.properties"));

		String charsFile =  wordsPath +"/sim_dict";
		EncryptDictionary ed = new EncryptDictionary();
		//System.out.println("load resource file name \t" + charsFile);
		log.info("similarity words file path  " + charsFile);
		if (true == ed.decryptOrderWordFile(charsFile)) {
			
			int tag_falg = 0;
			String textLine = "";
			for(int i = 0; i < ed.orderArray.size(); i++) {
				textLine = this.toUtf8(ed.orderArray.get(i).toString());
				//System.out.println("count number and text \t" + i + "\t" + textLine);
				if (textLine.equals("") == false) {
					if (textLine.startsWith("#")) {
						tag_falg++;
					} else {
						if (tag_falg == 1) {
							int idx = textLine.indexOf("\t");
							if (idx != -1) {
								String word = textLine.substring(0, idx);
								textLine = textLine.substring(idx + 1);
								if("".equals(textLine) == true || textLine.isEmpty() == true) {
									
									Vector<String> vec = new Vector<String>();
									vec.add(word);
									words_sim_arr_ch.put(word, vec);
								}
								else {
									String arrays[] = textLine.split(";");
									Vector<String> vec = new Vector<String>();
									vec.add(word);
									for (int k = 0; k < arrays.length; k++) {
										String iner_arr[] = arrays[k].split(":");
										if (iner_arr.length == 2) {
											vec.add(iner_arr[0]);
										}
									}
									words_sim_arr_ch.put(word, vec);
								}
							}
						} else if (tag_falg == 2) {
							int idx = textLine.indexOf("\t");
							if (idx != -1) {
								String word = textLine.substring(0, idx);
								textLine = textLine.substring(idx + 1);
								if("".equals(textLine) == true || textLine.isEmpty() == true) {
									
									Vector<String> vec = new Vector<String>();
									vec.add(word);
									words_sim_arr_en.put(word, vec);
								}
								else {
									String arrays[] = textLine.split(";");
									Vector<String> vec = new Vector<String>();
									vec.add(word);
									for (int k = 0; k < arrays.length; k++) {
										String iner_arr[] = arrays[k].split(":");
										if (iner_arr.length == 2) {
											vec.add(iner_arr[0]);
										}
									}
									words_sim_arr_en.put(word, vec);
								}
							}
						}
					}
				}
			}
			log.info("sim words loaded time=" + (System.currentTimeMillis() - start_time) + "ms, line=" + ed.orderArray.size()
			+ ", size ={ " + words_sim_arr_ch.size() + " , " + words_sim_arr_en.size() + " }");
		}
		else {
			log.info("load similarity words dicttionary is error!!!!! " + charsFile);
		}
		
	}

	public List<String> getChinCharWords(String word) {
		List<String> resultList = null;
		if (null != word && !"".equals(word)) {
			if (words_sim_arr_ch.containsKey(word)) {
				resultList = words_sim_arr_ch.get(word);
			}
		}
		return resultList;
	}

	public List<String> getEngCharWords(String word) {
		List<String> resultList = null;
		if (null != word && !"".equals(word)) {
			if (words_sim_arr_en.containsKey(word)) {
				resultList = words_sim_arr_en.get(word);
			}
		}
		return resultList;
	}

	public Set<String> getChinCharVocab() {
		Set<String> vocab = new HashSet<String>();
		for (String key : words_sim_arr_ch.keySet()) {
			vocab.add(key);
		}
		return vocab;
	}

	public Set<String> getEnglishCharVocab() {
		Set<String> vocab = new HashSet<String>();
		for (String key : words_sim_arr_en.keySet()) {
			vocab.add(key);
		}
		return vocab;
	}
}
