package com.mdip.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mdip.timedTask.listeners.DataFileStatusListener;
import com.mdip.timedTask.monitor.DataFileStatusMonitor;
import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.biz.joblog.service.IJobLogService;
import com.mdip.web.biz.joblog.service.impl.JobLogServiceImpl;

public class DataFileStatusMonitorTest {
	
//	public static void main(String args[]) throws Exception{
//		DataFileStatusMonitor monitor=new DataFileStatusMonitor(10000);		
//		monitor.monitor("D:/tmp/KettleDir/nankuang_product", new DataFileStatusListener());
//		monitor.start();
//	}
}
