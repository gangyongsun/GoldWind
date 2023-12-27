package com.mdip.modules.output;

import java.util.ArrayList;
import java.util.List;

import com.mdip.common.util.FileUtil;

public class OutputBusiness {
	/**
	 * 解析产量历史数据二进制REC文件
	 * <p>
	 * 需求上只解析*D.REC文件
	 * 
	 * @param recordFile
	 *            文件名
	 * @return 包含三班产量、运行时间、运载时间列表的列表
	 */
	public static List<List<Float>> getHistoryOutputList(String recordFile) {
		byte[] bytes = FileUtil.toByteArrayWithNIO(recordFile);

		int shiftNum = 3;
		int columnNum = 3;
		List<List<Float>> superList = new ArrayList<List<Float>>();
		List<Float> shiftColumn = null;

		for (int i = 0; i < shiftNum; i++) {
			shiftColumn = new ArrayList<Float>();
			for (int j = 0; j < columnNum; j++) {
				byte[] value = new byte[] { bytes[j * 120 + i * 12 + 0], bytes[j * 120 + i * 12 + 1], bytes[j * 120 + i * 12 + 2], bytes[j * 120 + i * 12 + 3] };
				float value2 = FileUtil.byte2float(value, 0);
				shiftColumn.add(value2);
			}
			superList.add(shiftColumn);
			shiftColumn = null;
		}
		return superList;
	}

}
