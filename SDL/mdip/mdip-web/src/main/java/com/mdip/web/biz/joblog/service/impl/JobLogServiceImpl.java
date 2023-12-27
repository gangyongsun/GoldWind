package com.mdip.web.biz.joblog.service.impl;

import java.io.Serializable;
import java.util.List;

import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.common.util.MyStringUtil;
import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.biz.joblog.service.IJobLogService;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.BaseService;
import com.mdip.web.framework.base.service.IPageService;

@Service("jobLogService")
public class JobLogServiceImpl extends BaseService implements IJobLogService {
	@Autowired
	protected IPageService pageService;

	@Autowired
	protected IDao jobLogDao;

	private static final String and = " and ";

	/**
	 * 查询所有joblog
	 */
	@Override
	public List<JobLogEntity> getAllJobLogList() {
		String where = "jobStatus = '" + Trans.STRING_RUNNING + "'";
		List<JobLogEntity> list = jobLogDao.queryList("JobLogEntity", where);
		return list;
	}

	/**
	 * 根据name查询job log
	 */
	@Override
	public List<JobLogEntity> getJobLogsByName(String jobName) {
		jobName = jobName.trim();
		StringBuffer where = new StringBuffer();
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
		}
		return jobLogDao.queryList("JobLogEntity", where.toString());
	}

	/**
	 * 根据id name查询job log
	 */
	@Override
	public List<JobLogEntity> getJobLogsByIdName(String logId, String jobName) {
		logId = logId.trim();
		jobName = jobName.trim();
		StringBuffer where = new StringBuffer();
		if (!"".equals(logId)) {
			where.append("id ='").append(logId).append("'");
			where.append(!"".equals(logId) ? and : "");
		}
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
		}
		String finalWhere = MyStringUtil.finalWhere(where.toString());// 判断where是否是以and结尾，如果是就要处理
		return jobLogDao.queryList("JobLogEntity", finalWhere);
	}

	/**
	 * 根据条件查询
	 */
	@Override
	public List<JobLogEntity> getJobLogsByConditions(String jobName, String startDatetime, String endDatetime, String jobStatus) {
		jobName = jobName.trim();
		startDatetime = startDatetime.trim();
		endDatetime = endDatetime.trim();
		jobStatus = jobStatus.trim();

		StringBuffer where = new StringBuffer();
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
			where.append(!"".equals(jobName) ? and : "");
		}
		if (!"".equals(startDatetime)) {
			where.append("startTime >='").append(startDatetime).append("'");
			where.append(!"".equals(startDatetime) ? and : "");
		}
		if (!"".equals(endDatetime)) {
			where.append("endTime <='").append(endDatetime).append("'");
			where.append(!"".equals(endDatetime) ? and : "");
		}
		if (!"".equals(jobStatus)) {
			where.append("jobStatus ='").append(jobStatus).append("'");
		}
		String finalWhere = MyStringUtil.finalWhere(where.toString());// 判断where是否是以and结尾，如果是就要处理
		return jobLogDao.queryList("JobLogEntity", finalWhere);
	}

	/**
	 * 根据logId查询jobEntity
	 */
	@Override
	public JobLogEntity getJobLogById(String logId) {
		return jobLogDao.queryOneById(JobLogEntity.class, logId);
	}

	@Override
	public Boolean update(JobLogEntity entity) {
		return jobLogDao.update(entity);
	}

	@Override
	public Boolean save(JobLogEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JobLogEntity queryById(String id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobLogEntity> queryByWhere(JobLogEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteById(Serializable id, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteByIds(String ids, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(JobLogEntity entity, PageEntity pageentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPageRe(JobLogEntity entity, PageEntity pageentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean deleteByIdsRe(String ids, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<JobLogEntity> getAll(JobLogEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(String hql, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPage(String tableName, String where, String orderBy, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

}
