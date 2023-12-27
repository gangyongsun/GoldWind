package com.mdip.web.biz.joblog.dao;

import org.springframework.stereotype.Service;

import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.framework.base.dao.BaseDao;
import com.mdip.web.framework.base.dao.IDao;

@Service
public class JobLogDao extends BaseDao implements IDao {

	@Override
	protected Class<JobLogEntity> getEntityClass() {
		return JobLogEntity.class;
	}

}