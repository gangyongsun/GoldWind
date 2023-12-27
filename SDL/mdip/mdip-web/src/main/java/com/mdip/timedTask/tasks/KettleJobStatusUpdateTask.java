package com.mdip.timedTask.tasks;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimerTask;

import org.pentaho.di.job.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.mdip.web.biz.kettlemanage.service.IKettleService;
import com.mdip.web.biz.kettlemanage.service.impl.KettleServiceImpl;
import com.mdip.web.framework.base.cache.CacheUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时更新Kettle作业状态
 * 
 * @author yonggang
 *
 */
@Slf4j
public class KettleJobStatusUpdateTask extends TimerTask {
	@Autowired
	@Qualifier("kettleService")
	IKettleService kettleService;

	@Override
	public void run() {
		Map<String, Job> jobMap = KettleServiceImpl.getJobMap();// (Map<String, Job>) CacheUtils.get(KettleServiceImpl.KETTLE_CACHE, KettleServiceImpl.KETTLE_JOB_MAP);
		if (null != jobMap && jobMap.size() != 0) {
			log.info("###ehCache中JobMap不为空，JobMap中有:" + jobMap.size() + "个对象！");
			Set<Entry<String, Job>> entrys = jobMap.entrySet();
			for (Entry<String, Job> entry : entrys) {
				log.info("###JobId：" + entry.getKey() + ",jobStatus:" + entry.getValue().getStatus());
			}
		} else {
			log.info("###ehCache中JobMap为空，没有job对象存在！");
		}
	}

}
