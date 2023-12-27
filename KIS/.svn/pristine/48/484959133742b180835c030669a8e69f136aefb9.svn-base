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
 * @author adolf  20191205
 *
 */
public class SimilarityWordsCalculation {
	
	  /**
     * 获取离线的相似词典
     * @param fileName
     * @param outfile
     * @return
     */
    public boolean getOfflineSimDict(String fileName, String outfile) {
    	if(fileName.equals("") == true || outfile.equals("") == true) {
    		return false;
    	}
    	
        try {
        	
        	FileInputStream fis = new FileInputStream(fileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String textLine = "";
            Set<String> tmp_item_dict = new HashSet<String>();
            List<String> arr_item = new ArrayList<String>();
            while ((textLine = br.readLine()) != null) {
            	if(textLine.length() > 1) {
            		String arrays[] = textLine.split(";");
            		for(int i = 0; i < arrays.length; i++) {
            			arr_item.add(arrays[i]);
                		tmp_item_dict.add(arrays[i]);
            		}
            	}
            }
            
            FileOutputStream fos = new FileOutputStream(new File(outfile));
            OutputStreamWriter os = new OutputStreamWriter(fos,"UTF-8");
            BufferedWriter bw = new BufferedWriter(os);
            
            for (int idx = 0; idx < arr_item.size(); idx++) {
            	String current_item = arr_item.get(idx);
            	bw.write(current_item + "\t");
            	Map<String,Float> ret_arrs = this.getTermTopRankScore(current_item.toLowerCase(),tmp_item_dict,10);
            	for(String key: ret_arrs.keySet())
                bw.write( key + ":"+ ret_arrs.get(key) + ";");
            	bw.write("\n");
            }
            System.out.println("dict size is " + tmp_item_dict.size());
			
            br.close();
            isr.close();
            fis.close();
            bw.close();
            os.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return  true;
    }
	
    /**
     * 返回分数最高的前size个分词结果和对应的rank
     * @param content
     * @param size
     * @return
     */
    public Map<String,Float> getTermTopRankScore(String content, Set<String> tmp_dict, int size)
    {
        
        Map<String, Float> score_map = new HashMap<String, Float>();
        
        for(String item: tmp_dict) {
        	item = item.toLowerCase();
        	if(content.equals(item) == false) {
        		// 线上要匹配分词快
        		float sim_score = (float)LVdistance.levenshtein(content,item);
        		score_map.put(item, sim_score);
        	}
        } 
        
        Map<String, Float> result = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> entry : new MaxHeap<Map.Entry<String, Float>>(size, new Comparator<Map.Entry<String, Float>>()
        {
            @Override
            public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2)
            {
                return o1.getValue().compareTo(o2.getValue());
            }
        }).addAll(score_map.entrySet()).toList())
        {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimilarityWordsCalculation sim_cal = new SimilarityWordsCalculation();
		sim_cal.getOfflineSimDict("/Users/adolf/eclipse-workspace/KeyInformationSystem/data/text_res.txt", 
								  "/Users/adolf/eclipse-workspace/KeyInformationSystem/data/sim_dict.txt");
	}

}
