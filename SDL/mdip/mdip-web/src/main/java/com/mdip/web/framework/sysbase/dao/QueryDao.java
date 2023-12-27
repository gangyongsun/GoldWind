package com.mdip.web.framework.sysbase.dao;

import org.springframework.stereotype.Service;

import com.mdip.web.framework.base.dao.BaseDao;
import com.mdip.web.framework.base.dao.IDao;

@Service
public class QueryDao extends BaseDao implements IDao {

	public QueryDao() {
	}

	protected Class getEntityClass() {
		return null;
	}

}
