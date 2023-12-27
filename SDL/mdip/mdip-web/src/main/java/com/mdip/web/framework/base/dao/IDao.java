package com.mdip.web.framework.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.persistence.EntityManager;

import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.base.exception.DaoException;

public abstract interface IDao {
	public abstract EntityManager getEm();

	/**
	 * 
	 * @param table
	 * @param where
	 * @param orderb
	 * @param paramInt1
	 * @param paramInt2
	 * @return
	 * @throws DaoException
	 */
	public abstract List queryListByWhere(String table, String where, String orderb, int paramInt1, int paramInt2) throws DaoException;

	public abstract int queryCountByWhere(String paramString1, String paramString2) throws DaoException;

	/**
	 * 获取记录总数
	 * 
	 * @return
	 */
	public Long getCount();

	/**
	 * 清除一级缓存的数据
	 */
	public void clear();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public Boolean insert(BaseEntity entity);

	/**
	 * 删除实体(物理删除) *
	 * 
	 * @param id
	 *            实体id数组
	 */
	public Boolean delete(Serializable id) throws DaoException;

	/**
	 * 根据等值删除实体 (物理删除)
	 * 
	 * @param propertyName
	 *            字段名
	 * @param value
	 *            字段值
	 * @throws DaoException
	 */
	public Boolean deleteByProperty(String propertyName, Object value) throws DaoException;

	/**
	 * 根据给定的多个字段等值删除数据 (物理删除)
	 * 
	 * @param hashmap
	 *            <字段名，字段值>
	 * @throws DaoException
	 */
	public Boolean deleteByProperty(HashMap<String, Object> hashmap) throws DaoException;

	/**
	 * 根据指定字段等值查找信息
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List findByProperty(String propertyName, Object value) throws DaoException;

	/**
	 * 
	 * @param hashmap
	 *            <字段名，字段值>
	 * @return ArrayList
	 * @throws DaoException
	 */
	public List findByProperty(HashMap<String, Object> hashmap) throws DaoException;

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	public Boolean update(BaseEntity entity);

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	public BaseEntity load(String id);

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	public BaseEntity load(int id);

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	public BaseEntity load(long id);

	/**
	 * 单表查询分页得结果集 专为分页使用 （方法1）
	 * 
	 * @param className
	 *            数据库名
	 * @param where
	 *            查询条件。如果没有条件请用"1＝1"代替
	 * @param start
	 *            起始值
	 * @param length
	 *            取多少条数据
	 * @return List 查询的结果集
	 * @throws DataAccessException
	 * 
	 */
	public List queryList(String className, String where, int start, int length) throws DaoException;

	public List queryList(String className, String where) throws DaoException;

	public List queryList(String className) throws DaoException;

	/**
	 * 单表查询分页得得此查询的总数据 配合queryList( String className,String where,int start,int
	 * length)使用 专为分页使用 （方法1） 单表查询分页
	 * 
	 * @param className
	 *            数据库名 单表
	 * @param where
	 *            查询条件。如果没有条件请用"1＝1"代替
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryCount(String className, String where) throws DaoException;

	/**
	 * 多表连接查询功能（方法2） 按指定条件得到结果集 分页专用
	 * 
	 * @param swhere
	 *            指定要返回的结果，比较两个表连接查询时dome1 dome1 ；如果要返回dome1表值，此swhere参数即为dome1
	 * @param className
	 *            多表 "deom1 d1,dome2 d2"
	 * @param where
	 *            查询条件
	 * @param orderb
	 *            排列条件
	 * @param start
	 *            起始值
	 * @param length
	 *            取多少条数据
	 * @return
	 * @throws DataAccessException
	 */
	public List queryListByWhere(String swhere, String className, String where, String orderb, int start, int length) throws DaoException;

	/**
	 * 多表连接查询功能（方法2）得到所有的结果集 是queryListByWhere方法的补充 分页专用
	 * 
	 * @param swhere
	 *            此参数无效（ 此方法是得到连接查询的所有结果集）；只为配合使用
	 * @param className
	 *            多表 "deom1 d1,dome2 d2"
	 * @param where
	 *            查询条件
	 * @param orderb
	 *            排列条件
	 * @param start
	 *            起始值
	 * @param length
	 *            取多少条数据
	 * @return
	 * @throws DataAccessDaoException
	 */
	public List queryListByAll(String swhere, String className, String where, String orderb, int start, int length) throws DaoException;

	/**
	 * 配合分页专用方法2使用，得到当前结果集的总数 queryListByAll 、queryListByWhere 分页时共此方法
	 * 
	 * @param swhere
	 * @param className
	 * @param where
	 * @param orderb
	 * @return
	 * @throws DataAccessException
	 */
	public Long queryCountByWhere(String swhere, String className, String where, String orderb) throws DaoException;

	/**
	 * 
	 * @param hql
	 *            直接方言查询 请根据不同的方言写此查询。如HQL语句
	 * @return
	 * @throws DataAccessException
	 */
	public List query(String hql) throws DaoException;

	/**
	 * 根据主键id查询单条记录
	 * 
	 * @param entityClass
	 * @param primaryKey
	 * @return
	 */
	public <T> T queryOneById(Class<T> entityClass, Object primaryKey);

	/**
	 * 
	 * @param sql
	 *            直接标准sql查询
	 * @return
	 */
	public Collection sqlSelect(String sql) throws DaoException;

	/**
	 * 
	 * @param sql
	 *            直接标准sql更新
	 * @throws DataAccessDaoException
	 */
	public Boolean sqlUpdate(String sql) throws DaoException;

	/**
	 * 
	 * @param sql
	 *            直接标准sql删除
	 * @throws DataAccessDaoException
	 */
	public Boolean sqlDelete(String sql) throws DaoException;

	/**
	 * 
	 * @param hql
	 * @throws DataAccessDaoException
	 */
	public Boolean hqlDelete(String hql) throws DaoException;

	/**
	 * 
	 * @param hql
	 * @throws DataAccessDaoException
	 */
	public Boolean hqlUpdate(String hql) throws DaoException;
}