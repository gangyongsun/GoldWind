package com.mdip.web.framework.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.IPageService;

/**
 * 分页查询Service接口实现
 * 
 * @author yonggang
 *
 */
@Service("pageService")
public class PageServiceImpl extends PageEntity implements IPageService {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULTPAGESIZE = 20;// 默认页面条数为20条

	@Autowired
	private IDao queryDao;
	
	/**
	 * 默认构造
	 */
	private PageServiceImpl() {

	}

//	public PageEntity doList(String className, String where) {
//		this.setTotalRecord(this.iDao.queryCount(className, where).longValue());
//		if (this.getTotalRecord() != 0L) {
//			//this.pageNumber = ((this.count % this.pageSize.intValue() != 0L) ? this.count / this.pageSize.intValue() + 1L : this.count / this.pageSize.intValue());
//			this.setTotalPage((this.getTotalRecord()-1)/this.getPageSize()+1);
//			if (this.getCurrentPage() > this.getPageSize()) {
//				this.setCurrentPage(this.getTotalPage());
//			}
//		}
//
//		setResult(this.iDao.queryList(className, where, (this.getCurrentPage() - 1) * this.getPageSize(), this.getPageSize()));
//
//		return this;
//	}
//
//	public PageEntity doListbywhere(String swhere, String className, String where, String orderb) {
//		this.count = this.iDao.queryCountByWhere(swhere, className, where, orderb).longValue();
//
//		if (this.count != 0L) {
//			this.totalPage = ((this.count % this.getPageSize() != 0L) ? this.count / this.getPageSize() + 1L : this.count / this.getPageSize());
//			if (this.getCurrentPage() > this.totalPage) {
//				this.currentPage = Integer.valueOf((int) this.totalPage);
//			}
//		}
//
//		setResult(this.iDao.queryListByWhere(swhere, className, where, orderb, (this.getCurrentPage() - 1) * this.getPageSize(), this.getPageSize()));
//
//		return this;
//	}

//	public void getPage(int currentPage) {
//		getPage(20, currentPage);
//	}
//
//	public void getPage(int pageSize, int currentPage) {
//		this.currentPage = Integer.valueOf(1);
//		this.totalPage = 1L;
//		this.count = 0L;
//		this.pageSize = Integer.valueOf((pageSize > 0) ? pageSize : 20);
//
//		this.currentPage = Integer.valueOf((currentPage > 0) ? currentPage : currentPage);
//	}
//
//	public void refreshUrl() {
//	}

	@Override
	public PageEntity doListbywhere(String table, String where, String orderb) throws ServiceException {
		this.setTotalRecord(this.queryDao.queryCountByWhere(table, where));

		if (this.getTotalRecord() != 0L) {
			this.setTotalPage((this.getTotalRecord()-1)/this.getPageSize()+1);
			if (this.getPageSize() > this.getTotalPage()) {
				this.setCurrentPage(this.getTotalPage());
			}
		}

		setResult(this.queryDao.queryListByWhere(table, where, orderb, (this.getCurrentPage() - 1) * this.getPageSize(), this.getPageSize()));

		return this;
	}

	@Override
	public void getPage(int pageSize, int currentPage) throws ServiceException {
		this.setTotalPage(1);
		this.setTotalRecord(0);
		this.setPageSize((pageSize > 0) ? pageSize : 20);

		this.setCurrentPage((currentPage > 0) ? currentPage : 1);
		
	}

	@Override
	public void getPage(int currentPage) throws ServiceException {
		getPage(currentPage,currentPage);
		
	}

	@Override
	public PageEntity doList(String paramString1, String paramString2) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageEntity doListbywhere(String paramString1, String paramString2, String paramString3, String paramString4)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
