package com.mdip.web.biz.tabledata.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdip.web.biz.tabledata.service.ITableDataService;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.BaseService;
import com.mdip.web.framework.base.service.IPageService;

@Service("tableDataService")
@Transactional
public class TableDataServiceImpl extends BaseService implements ITableDataService {

	@Autowired
	protected IDao queryDao;

	@Autowired
	protected IPageService pageService;

	@Override
	public Boolean update(BaseEntity paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean save(BaseEntity paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BaseEntity queryById(String paramString) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List queryByWhere(BaseEntity paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteById(Serializable paramSerializable, String paramString) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteByIds(String paramString1, String paramString2) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(BaseEntity paramT, PageEntity pageEntity) {

	}

	@Override
	public void getPageRe(BaseEntity paramT, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean deleteByIdsRe(String paramString1, String paramString2) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List getAll(BaseEntity paramT) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(String hql, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPage(String tableName, String where, String orderBy, PageEntity pageEntity) {
		this.pageService.getPage(pageEntity.getPageSize(), pageEntity.getCurrentPage());
		PageEntity init = this.pageService.doListbywhere(tableName, where, orderBy);
		setpage(pageEntity, init);
	}

}
