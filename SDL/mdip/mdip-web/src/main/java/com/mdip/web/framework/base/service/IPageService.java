package com.mdip.web.framework.base.service;

import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;

public interface IPageService {

	/**
	 * 第一步 使用分页先使用此方法初始化分页服务参数；
	 * 
	 * 
	 * @param pageSize
	 *            一页显示多少条记录 如果传为小于或等于0，则按默认显示20条
	 * @param currentpage
	 */
	public void getPage(int pageSize, int currentpage) throws ServiceException;

	/**
	 * 第一步 使用分页先使用此方法初始化分页服务参数；
	 * 
	 * 
	 *
	 * @param currentpage
	 *            当前页码
	 */
	public void getPage(int currentpage) throws ServiceException;

	/**
	 * 第二步 单表查询分页使用此方法
	 * 
	 * @param className
	 * @param where
	 */
	public PageEntity doList(String className, String where) throws ServiceException;

	/**
	 * 第二步 连接查询分页使用此方法。并指定返回表结果集
	 * 
	 * @param swhere
	 * @param className
	 * @param where
	 * @param orderb
	 */
	public PageEntity doListbywhere(String swhere, String className, String where, String orderb) throws ServiceException;

	/**
	 * 第二步 连接查询分页使用此方法。并指定返回表结果集
	 * 
	 * @param countWhere
	 *            指定要返回统计的不重复的记录条数，比较两个表连接查询时dome1 dome2
	 *            ；如果要返回dome1表值，此countWhere参数即为dome1，默认按dome1的id进行查找统计
	 * @param swhere
	 *            指定要返回的结果，比较两个表连接查询时dome1 dome2
	 *            ；如果要返回dome1表值，此swhere参数即为dome1；或者为多个字段
	 * @param className
	 * @param where
	 * @param orderb，比如：orderb为"
	 *            order by uq.appId"
	 *            例如：doListbySelectwhere("uq","ea.appliname,ea.applicode,uq.sydate,uq.workcontent,uq.time,uq.startstate,uq.endstate,uq.name,uq.id,uq.appId,ea.syunit,ea.sydept",
	 *            " EquAppliance ea,EquUserecord uq", " ea.id=uq.appId", " order
	 *            by uq.appId");
	 */
	// public PageEntity doListbySelectwhere(String countWhere,String swhere,
	// String className, String where, String orderb)throws Exception;
	/**
	 * 第二步 连接查询分页使用此方法。返回所有结果集
	 * 
	 * @param swhere
	 * @param className
	 * @param where
	 * @param orderb
	 */

	public PageEntity doListbywhere(String paramString1, String paramString2, String paramString3) throws ServiceException;
}