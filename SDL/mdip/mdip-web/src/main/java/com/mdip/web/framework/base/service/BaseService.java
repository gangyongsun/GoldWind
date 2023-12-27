package com.mdip.web.framework.base.service;

import com.mdip.web.framework.base.entity.PageEntity;

public abstract class BaseService {
	protected void setpage(PageEntity pageentity, PageEntity init) {
		if (init != null) {
			pageentity.setTotalRecord((init.getTotalRecord()));
			pageentity.setTotalPage(init.getTotalPage());
			pageentity.setResult(init.getResult());
		}
	}
}