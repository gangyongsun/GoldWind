package com.mdip.timedTask.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.mdip.timedTask.timerMgr.KettleBakfileCleanTimerManager;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务，删除kettle作业文件目录里bak下超过时长的文件
 * 
 * @author yonggang
 *
 */
@Slf4j
public class KettleBakfileCleanTaskListener implements ServletContextListener {

	/**
	 * 初始化执行定时任务
	 */
	public void contextInitialized(ServletContextEvent sce) {
		log.info("kettle处理备份文件定时任务启动...");
		new KettleBakfileCleanTimerManager();
		log.info("kettle处理备份文件定时任务启动成功！");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("KettleBakfileCleanTaskListener destroy");
	}

}