package com.mdip.web.biz.kettlemanage.service;

import java.util.List;

import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.framework.base.service.ICRUDService;

public interface IKettleService extends ICRUDService<KettleJobEntity> {

	public boolean startJobById(String jobId);

	public boolean stopJobById(String jobId);
	
	public boolean delteJobById(String jobId);

	public String getJobStatusById(String jobId);

	public KettleJobEntity getJobInfoById(String jobId);

	public boolean saveJobInfo(KettleJobEntity jobInfoEntity);

	public List<KettleJobEntity> getAllJobInfo();

	public List<KettleJobEntity> getJobsByIdName(String jobId, String jobName);

	public List<KettleJobEntity> getJobsByName(String jobName);

	public List<KettleJobEntity> getJobsByConditions(String jobName, String jobType, String runStatus);

	public int getJobCount();

	public int getJobCountByStatus(String runnStatus);

	public List<KettleJobEntity> getJobsByRunStatus(String runStatus);
}
