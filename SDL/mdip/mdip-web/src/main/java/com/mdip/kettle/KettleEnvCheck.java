package com.mdip.kettle;

import com.mdip.common.util.FileUtil;
import com.mdip.common.util.PropertiesUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 初始化kettle抽取前，进行必要的环境检查
 * 
 * @author Bill
 * @date Jun 26, 2017
 */
@Slf4j
public class KettleEnvCheck {
	private static final String JobPropertyFile = "kettle.properties";// 各个Job作业中的配置文件
	private static final String ConfigPropertyFile = "kettle.properties";// mdip-kettle项目中的Kettle配置文件
	public static final String Seperator = "/";
	private static final String WorkDir = PropertiesUtil.getValueByKey("job.dir.work", ConfigPropertyFile);
	private static final String LogDir = PropertiesUtil.getValueByKey("job.dir.log", ConfigPropertyFile);
	private static final String BakDir = PropertiesUtil.getValueByKey("job.dir.bak", ConfigPropertyFile);
	private static final String JobsDir = PropertiesUtil.getValueByKey("job.dir.jobs", ConfigPropertyFile);
	private static final String FailDir = PropertiesUtil.getValueByKey("job.dir.fail", ConfigPropertyFile);
	private static final String RealDir = PropertiesUtil.getValueByKey("job.dir.real", ConfigPropertyFile);
	private static final String HistoryDir = PropertiesUtil.getValueByKey("job.dir.history", ConfigPropertyFile);
	private static final String DbDir = PropertiesUtil.getValueByKey("job.dir.db", ConfigPropertyFile);

	/**
	 * 根据jobName初始化作业运行环境
	 * 
	 * @param jobDir
	 * @return
	 * @throws Exception
	 */
	public static boolean initByJobName(String jobDir) throws Exception {
		boolean flag = false;
		FileUtil.createFolder(jobDir);// 根据作业名称检查该作业根文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + WorkDir);// 检查work文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + LogDir);// 检查log文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + BakDir);// 检查bak文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + JobsDir);// 检查jobs文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + FailDir);// 检查fail文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + RealDir);// 检查real文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + HistoryDir);// 检查history文件夹是否存在,没有则创建
		FileUtil.createFolder(jobDir + DbDir);// 检查db文件夹是否存在,没有则创建

		flag = FileUtil.fileExist(jobDir + JobsDir + Seperator + JobPropertyFile);// 检查properties文件是否存在
		if (!flag) {
			log.info("作业缺少配置文件，启动作业出错");
			throw new Exception("缺少配置文件");
		}
		return flag;
	}

}
