package com.mdip.timedTask.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mdip.timedTask.timerMgr.KettleJobStatusUpdateTimerManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务，查询job状态，同步到数据库
 * @author yonggang
 *
 */
@Slf4j
public class KettleJobStatusUpdateTaskListener implements ServletContextListener {

	/**
	 * 初始化执行定时任务
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("job状态监测定时任务启动...");
		new KettleJobStatusUpdateTimerManager();
		log.info("job状态监测定时任务启动成功...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("KettleJobStatusUpdateTaskListener destroy");
	}

}
