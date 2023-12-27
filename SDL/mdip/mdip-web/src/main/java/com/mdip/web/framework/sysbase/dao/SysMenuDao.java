package com.mdip.web.framework.sysbase.dao;

import org.springframework.stereotype.Service;

import com.mdip.web.framework.base.dao.BaseDao;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.sysbase.entity.SysMenu;

@Service
public class SysMenuDao extends BaseDao implements IDao {
	@Override
	protected Class getEntityClass() {
		// TODO Auto-generated method stub
		return SysMenu.class;
	}
}