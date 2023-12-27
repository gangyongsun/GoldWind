package com.mdip.web.biz.joblog.service;

import java.util.List;

import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.framework.base.service.ICRUDService;

public interface IJobLogService extends ICRUDService<JobLogEntity> {

	public List<JobLogEntity> getAllJobLogList();

	public List<JobLogEntity> getJobLogsByName(String jobName);

	public List<JobLogEntity> getJobLogsByIdName(String jobId, String jobName);

	public JobLogEntity getJobLogById(String logId);

	public List<JobLogEntity> getJobLogsByConditions(String jobName, String startDatetime, String endDatetime, String jobStatus);
}
