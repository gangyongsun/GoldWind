package com.mdip.timedTask.timerMgr;

import java.util.Timer;

import com.mdip.common.util.PropertiesUtil;
import com.mdip.timedTask.tasks.KettleJobStatusUpdateTask;

public class KettleJobStatusUpdateTimerManager {

	private static final String timeInterval=PropertiesUtil.getValueByKey("jobStatusTimeInterval", "timetask.properties");
	private static final long interval =Integer.parseInt(timeInterval.trim())* 1000L;// 定时任务时间间隔,单位为毫秒

	/**
	 * 构造方法
	 */
	public KettleJobStatusUpdateTimerManager() {
		new Timer().schedule(new KettleJobStatusUpdateTask(), 0, interval);
	}
}
