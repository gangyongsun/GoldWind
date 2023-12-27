package com.mdip.web.framework.base.service;

import java.io.Serializable;
import java.util.List;

import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;

public interface ICRUDService<T extends BaseEntity<T>> {

	/**
	 * 更新接口
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean update(T entity);

	/**
	 * 保存接口
	 * 
	 * @param entity
	 * @return
	 */
	public Boolean save(T entity);

	/**
	 * 得一个实体
	 * 
	 * @param id
	 *            实体id，如果没有返回NULL
	 */
	public T queryById(String id) throws ServiceException;

	/**
	 * 根据输入的内容根据所有结果
	 * 
	 * @param entity
	 * @return
	 */
	public abstract List<T> queryByWhere(T entity);

	/**
	 * 删除接口，根据ID删除
	 * 
	 * @param id
	 */
	public abstract Boolean deleteById(Serializable id, String update_by) throws ServiceException;

	/**
	 * 删除接口，根据IDs删除（逻辑删除）
	 * 
	 * @param ids
	 *            ,更新者
	 */
	public abstract Boolean deleteByIds(String ids, String update_by) throws ServiceException;

	/**
	 * 根据输入的内容根据所有结果,-带分页效果
	 * 
	 * @param entity
	 * @return
	 */
	public abstract void getPage(T entity, PageEntity pageentity);

	/**
	 * 查看回收站数据信息
	 * 
	 * @param entity
	 * @param pageentity
	 * @return
	 */
	public abstract void getPageRe(T entity, PageEntity pageentity);

	/**
	 * 逻辑删除根据IDS恢复接口
	 * 
	 * @param ids
	 * @param update_by
	 * @return
	 */
	public abstract Boolean deleteByIdsRe(String ids, String update_by) throws ServiceException;

	/**
	 * 根据条件查询所有为不空的数据
	 * 
	 * @param entity
	 * @return
	 */
	public List<T> getAll(T entity);

	public void getPage(String hql, PageEntity paramPageEntity);

	public void getPage(String tableName, String where, String orderBy, PageEntity paramPageEntity);

}
