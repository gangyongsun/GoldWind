package com.mdip.timedTask.timerMgr;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.timedTask.tasks.KettleBakfileCleanTask;

/**
 * 任务管理
 * 
 * @author yonggang
 * 
 */
public class KettleBakfileCleanTimerManager {
	// 定时任务时间间隔
	private static final String interval = PropertiesUtil.getValueByKey("execute.interval", "timetask.properties");
	// 子目录名,删除该目录下的过期文件
	private static final String subfolderName = PropertiesUtil.getValueByKey("folder.name", "timetask.properties");
	// 文件最后修改时间到现在的天数
	private static final String days = PropertiesUtil.getValueByKey("file.age", "timetask.properties");
	// 定时任务执行时间：小时
	private static final String hour = PropertiesUtil.getValueByKey("start.hour", "timetask.properties");
	// 定时任务执行时间：分钟
	private static final String minute = PropertiesUtil.getValueByKey("start.minute", "timetask.properties");
	// 定时任务执行时间：秒
	private static final String second = PropertiesUtil.getValueByKey("start.second", "timetask.properties");
	// 时间间隔转换为毫秒
	private static final long PERIOD_DAY = Integer.parseInt(interval.trim()) * 24 * 60 * 60 * 1000;

	/**
	 * 构造方法
	 */
	public KettleBakfileCleanTimerManager() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour.trim()));
		calendar.set(Calendar.MINUTE, Integer.parseInt(minute.trim()));
		calendar.set(Calendar.SECOND, Integer.parseInt(second.trim()));

		// 第一次执行定时任务的时间,如果第一次执行定时任务的时间 小于当前的时间,此时要在
		// 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行;如果不加一天，任务会立即执行
		Date date = calendar.getTime();
		if (date.before(new Date())) {
			date = DateUtil.add_days(date, 1);
		}

		Timer timer = new Timer();
		// 安排指定的任务在指定的时间开始进行重复的固定延迟执行
		timer.schedule(new KettleBakfileCleanTask(subfolderName, Integer.parseInt(days.trim()) * 24), date, PERIOD_DAY);
	}

}
