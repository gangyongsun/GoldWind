package cn.com.goldwind.kis.mmseg.sortfreq;

import java.util.Comparator;
import java.util.Map.Entry;

/** 
 * the class only compare the value of the map 
 * Frank suny    20141115
 */

public class MapValueComparator implements Comparator<Entry<String, String>>{

	 public int compare(Entry<String, String> me1, Entry<String, String> me2) {  
	      return me1.getValue().compareTo(me2.getValue());  
	 } 
}
