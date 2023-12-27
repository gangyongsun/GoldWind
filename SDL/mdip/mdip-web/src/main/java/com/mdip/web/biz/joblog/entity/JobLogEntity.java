package com.mdip.web.biz.joblog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.springframework.context.annotation.Description;

import com.mdip.web.framework.sysbase.entity.SysBaseEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "KETTLE_JOB_LOG")
@Description(value = "kettle日志信息")
public class JobLogEntity extends SysBaseEntity<JobLogEntity> {
	private static final long serialVersionUID = 1L;

	@Column(name = "JOBID")
	private String jobId;

	@Column(name = "JOBNAME")
	private String jobName;

	@Column(name = "STATUS")
	private String jobStatus;
	
	@Column(name = "STARTDATE")
	private String startTime;

	@Column(name = "ENDDATE")
	private String endTime;
	
	@Column(name = "FILES_READ")
	private int readFilesNum;
	
	@Column(name = "FILES_SUCCESS")
	private int successFilesNum;
	
	@Column(name = "FILES_FAIL")
	private int failFilesNum;
	
	@Column(name = "FILES_REAL_IN")
	private int realInCount;	
	
	@Column(name = "FILES_REAL_OUT")
	private int realOutCount;
	
	@Column(name = "FILES_REAL_SUM")
	private int realSum;
		
	@Column(name = "FILES_HISTORY_IN")
	private int historyInCount;	
	
	@Column(name = "FILES_HISTORY_OUT")
	private int historyOutCount;
	
	@Column(name = "FILES_HISTORY_SUM")
	private int historySum;
	
	@Column(name = "FILES_DB_IN")
	private int dbInCount;	
	
	@Column(name = "FILES_DB_OUT")
	private int dbOutCount;
	
	@Column(name = "FILES_DB_SUM")
	private int dbSum;
			

}
