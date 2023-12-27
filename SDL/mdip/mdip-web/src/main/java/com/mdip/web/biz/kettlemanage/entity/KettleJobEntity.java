package com.mdip.web.biz.kettlemanage.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.context.annotation.Description;

import com.mdip.web.framework.sysbase.entity.SysBaseEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "JOB_INFO")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Description(value = "kettle作业信息")
public class KettleJobEntity extends SysBaseEntity<KettleJobEntity> {
	private static final long serialVersionUID = 1L;

	@Column(name = "JOB_NAME")
	private String jobName;

	@Column(name = "JOB_FILE_DIR")
	private String jobFileDir;

	@Column(name = "JOB_FILE_NAME")
	private String jobFileName;

	@Column(name = "JOB_DESCRIPTION")
	private String jobDescription;

	@Column(name = "JOB_TYPE")
	private String jobType;

	@Column(name = "JOB_STATUS")
	private int jobStatus;

	@Column(name = "RUN_STATUS")
	private String runStatus;

	@Column(name = "LAST_UPDATE")
	private String lastUpdate;

	@Column(name = "START_TIME")
	private String startTime;

	@Column(name = "END_TIME")
	private String endTime;

	@Column(name = "RUN_TIME")
	private Long runTime;

	@Column(name = "LAST_RECORD_COUNT")
	private int lastRecordCount;

	@Column(name = "LOG_FOLDER_SIZE")
	private String logFolderSize;
}
