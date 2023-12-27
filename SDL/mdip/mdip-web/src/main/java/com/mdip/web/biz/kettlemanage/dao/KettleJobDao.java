package com.mdip.web.biz.kettlemanage.dao;

import org.springframework.stereotype.Service;

import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.framework.base.dao.BaseDao;
import com.mdip.web.framework.base.dao.IDao;

@Service
public class KettleJobDao extends BaseDao implements IDao {

	@Override
	protected Class<KettleJobEntity> getEntityClass() {
		return KettleJobEntity.class;
	}

}
