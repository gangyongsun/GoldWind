package com.mdip.timedTask.listeners;

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.mdip.common.util.FileUtil;
import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.biz.joblog.service.IJobLogService;
import com.mdip.web.biz.joblog.service.impl.JobLogServiceImpl;
import com.mdip.web.framework.base.util.SpringContextHolder;

public class DataFileStatusListener implements FileAlterationListener {
	
	private JobLogEntity entity;
	
	private File folder;
	
	private boolean isChanged;
	
	private int bakSum;
	
	private int successFileCount;
	
	private int failFileCount;
	
	private int bakInCount;
	
	private int bakOutCount;
	
	private int failSum;
	
	private int failInCount;
	
	private int failOutCount;
	
	private int realInCount;	
	
	private int realOutCount;
	
	private int realSum;
	
	private int historyInCount;	
	
	private int historyOutCount;
	
	private int historySum;
	
	private static final String BAK_DIR="bak";
	
	private static final String FAIL_DIR="fail";
	
	private static final String REAL_DIR="real";
	
	private static final String HISTORY_DIR="history";
	
	public DataFileStatusListener(){
		
	}
	public DataFileStatusListener(JobLogEntity entity){
		this.entity=entity;
	}
	public DataFileStatusListener(JobLogEntity entity, File folder){
		this.entity=entity;
		this.folder=folder;
	}
	
	@Override
	public void onStart(FileAlterationObserver observer) {
		if(isChanged){
			if(folder!=null){
				realSum=FileUtil.getFilesSum(folder+"/"+REAL_DIR);
				historySum=FileUtil.getFilesSum(folder+"/"+HISTORY_DIR);
				bakSum=FileUtil.getFilesSum(folder+"/"+BAK_DIR);
				failSum=FileUtil.getFilesSum(folder+"/"+FAIL_DIR);
			}
			successFileCount+=bakInCount;
			failFileCount+=failInCount;
			entity.setReadFilesNum(successFileCount+failFileCount);
			entity.setSuccessFilesNum(successFileCount);
			entity.setFailFilesNum(failFileCount);
			entity.setRealInCount(realInCount);
			entity.setRealOutCount(realOutCount);
			entity.setRealSum(realSum);
			entity.setHistoryInCount(historyInCount);
			entity.setHistoryOutCount(historyOutCount);
			entity.setHistorySum(historySum);
			
			IJobLogService jobLogService=(JobLogServiceImpl)SpringContextHolder.getBean("jobLogService");
			jobLogService.update(entity);
			
			isChanged=false;
			bakInCount=0;
			failInCount=0;
			realInCount=0;
			realOutCount=0;
			historyInCount=0;
			historyOutCount=0;
			
		}
	}

	@Override
	public void onDirectoryCreate(File directory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDirectoryChange(File directory) {
		isChanged=true;
	}

	@Override
	public void onDirectoryDelete(File directory) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFileCreate(File file) {
		String pFolder=file.getParent();
		if(pFolder.endsWith(BAK_DIR)){
			//bak目录
			bakInCount++;
		}else if(pFolder.endsWith(FAIL_DIR)){
			//fail目录
			failInCount++;
		}else if(pFolder.endsWith(REAL_DIR)){
			//real目录
			realInCount++;
		}else if(pFolder.endsWith(HISTORY_DIR)){
			//fail目录
			historyInCount++;
		}        
	}

	@Override
	public void onFileChange(File file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFileDelete(File file) {
		String pFolder=file.getParent();
		if(pFolder.endsWith("real")){
			//real目录
			realOutCount++;
		}else if(pFolder.endsWith("history")){
			//fail目录
			historyOutCount++;
		}

	}

	@Override
	public void onStop(FileAlterationObserver observer) {
		// TODO Auto-generated method stub

	}

}
