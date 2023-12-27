package com.mdip.timedTask.tasks;

import java.util.TimerTask;

import com.mdip.common.util.FileUtil;
import com.mdip.common.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 执行内容
 * 
 * @author yonggang
 * 
 */
@Slf4j
public class KettleBakfileCleanTask extends TimerTask {
	private String subFolderName;// 删除该目录下的过期文件
	private int hours;// 文件最后修改时间到现在的小时数

	public KettleBakfileCleanTask(String subFolderName, int hours) {
		this.subFolderName = subFolderName;
		this.hours = hours;
	}

	@Override
	public void run() {
		String kettleDir = PropertiesUtil.getValueByKey("kettle.dir", "kettle.properties");// 从kettle配置文件取出jobs上传路径
		log.info("deleting the files older than specified days for bak subfolders in " + kettleDir);
		FileUtil.deleteOldFilesInFolders(kettleDir, subFolderName, hours);// 删除bak目录里过时的文件
	}
}
