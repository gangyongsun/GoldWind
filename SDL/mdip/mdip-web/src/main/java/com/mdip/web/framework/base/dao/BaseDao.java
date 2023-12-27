package com.mdip.web.framework.base.dao;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EmbeddedId;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.base.exception.DaoException;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
public abstract class BaseDao implements IDao {
	public BaseDao() {
	}

	@PersistenceContext
	protected EntityManager em;

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	/**
	 * 得到当前操作的数据库表
	 * 
	 * @return
	 */
	protected abstract Class<?> getEntityClass();

	/**
	 * 清除一级缓存的数据
	 */
	public void clear() {
		em.clear();
	}

	/**
	 * 根据条件查询数量
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int queryCountByWhere(String table, String where) throws DaoException {
		if (StringUtils.isBlank(table)) {
			throw new DaoException("表名不能为空或null");
		}
		if (StringUtils.isBlank(where)) {
			where = "1=1";
		}

		String sql = "select count(1) from " + table + ((where == null) ? "" : new StringBuilder(" where ").append(where).toString());
		int count = 0;
		Session session = null;
		try {
			session = em.unwrap(org.hibernate.Session.class);
			List<?> list = session.createSQLQuery(sql).list();
			if (list != null) {
				count = Integer.parseInt(list.get(0).toString());
			}
			return count;
		} catch (Exception e) {
			log.error(sql, e);
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
			sql = null;
		}
	}

	/**
	 * 根据条件及页码查询结果
	 * 
	 * @param table
	 *            要查询的表名
	 * @param where
	 *            过滤条件
	 * @param orderb
	 *            根据哪个字段，升序还是降序（例如： 字段A ASC）
	 * @param start
	 *            开始记录
	 * @param length
	 *            取记录数
	 * @return 结果list
	 * @throws DaoException
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryListByWhere(String table, String where, String orderb, int start, int length) throws DaoException {
		if (StringUtils.isBlank(table)) {
			throw new DaoException("表名不能为空或null");
		}
		if (StringUtils.isBlank(where)) {
			where = "1=1";
		}
		if (StringUtils.isBlank(orderb)) {
			orderb = null;
		}
		String sql = "select * from " + table + ((where == null) ? "" : new StringBuilder(" where ").append(where).toString())
				+ ((orderb == null) ? "" : new StringBuilder(" order by ").append(orderb).toString());
		return queryListByWhere(sql, start, length);
	}

	/**
	 * 根据sql及页码查询结果
	 * 
	 * @param sql
	 *            sql字符串
	 * @param start
	 *            开始记录
	 * @param length
	 *            取记录数
	 * @return 结果list
	 * @throws DaoException
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryListByWhere(String sql, int start, int length) throws DaoException {
		Session session = null;
		try {
			session = em.unwrap(org.hibernate.Session.class);
			return session.createSQLQuery(sql).setFirstResult(start).setMaxResults(length).list();
		} catch (Exception e) {
			log.error(sql, e);
			e.printStackTrace();
			return null;
		} finally {
			session.close();
			sql = null;
		}
	}

	/**
	 * 获取记录总数
	 * 
	 * 实体类
	 * 
	 * @return
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Long getCount() {
		return (Long) em.createQuery("select count(" + getCountField(this.getEntityClass()) + ") from " + getEntityClass().getName() + " o").getSingleResult();
	}

	/**
	 * select * from table where name='1' and pwd='12' select * from table where
	 * name=1? and pwd =2?
	 * 
	 * @param hql
	 *            直接方言查询 请根据不同的方言写此查询。如HQL语句
	 * @return
	 * @throws DaoException
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> query(String hql) throws DaoException {
		if (StringUtils.isBlank(hql)) {
			throw new DaoException("参数hql不能为空或null");
		}
		try {
			Query query = em.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

	/**
	 * 单表查询分页得得此查询的总数据 配合queryList( String className,String where,int start,int
	 * length)使用 专为分页使用 （方法1） 单表查询分页
	 * 
	 * @param className
	 *            数据库名 单表
	 * @param where
	 *            查询条件。如果没有条件请用"1＝1"代替
	 * @return
	 * @throws DaoException
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Long queryCount(String className, String where) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		String hql = new StringBuffer("select count(o) from ").append(className).append(" o ").append((where != null) ? " where " + where : "").toString();
		try {
			return (Long) em.createQuery(hql).getSingleResult();
		} catch (Exception e) {
			log.error(hql, e);
			return null;
		} finally {
			hql = null;
		}
	}

	/**
	 * 配合分页专用方法2使用，得到当前结果集的总数 queryListByAll 、queryListByWhere 分页时共此方法
	 * 
	 * @param swhere
	 * @param className
	 * @param where
	 * @param orderb
	 * @return
	 * @throws DaoException
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Long queryCountByWhere(final String swhere, final String className, String where, final String orderb) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		if (StringUtils.isBlank(where)) {
			where = "1=1";
		}
		String hql = new StringBuffer("select count(").append(1).append(" ) from ").append(className).append(where == null ? "" : " where " + where).toString();
		try {
			return (Long) em.createQuery(hql).getSingleResult();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

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
	 * @throws DaoException
	 * 
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryList(final String className, final String where, final int start, final int length) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		String hql = new StringBuffer("from ").append(className).append(" o ").append(StringUtils.isNotBlank(where) ? "where " + where : "").toString();
		try {
			Query query = em.createQuery(hql);
			return query.setFirstResult(start).setMaxResults(length).getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

	@Override
	public List<?> findByProperty(String propertyName, Object value) throws DaoException {
		if (propertyName.equals("") || propertyName == null) {
			throw new DaoException("字段名不为能空");
		}
		String hql = new StringBuffer("select o from ").append(getEntityClass().getSimpleName()).append(" o where o.").append(propertyName).append("=?1").toString();
		try {
			Query query = em.createQuery(hql);
			query.setParameter(1, value);
			return query.getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

	@Override
	public List<?> findByProperty(HashMap<String, Object> hashmap) throws DaoException {
		if (hashmap.isEmpty()) {
			throw new DaoException("查询字段名不为能空");
		}
		StringBuffer hql = new StringBuffer("select o from ").append(getEntityClass().getSimpleName()).append(" o where 1=1 ");
		Iterator<?> it = hashmap.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			hql.append(" and ").append(entry.getKey().toString()).append("=:").append(entry.getKey().toString());
		}
		try {
			Query query = em.createQuery(hql.toString());
			Iterator<?> itv = hashmap.entrySet().iterator();
			while (itv.hasNext()) {
				Entry entry = (Entry) itv.next();
				query.setParameter(entry.getKey().toString(), entry.getValue());
			}
			return query.getResultList();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

	/**
	 * 多表连接查询功能（方法2）得到所有的结果集 分页专用
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
	 * @throws DaoException
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryListByAll(final String swhere, final String className, String where, final String orderb, final int start, final int length) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		if (StringUtils.isBlank(where)) {
			where = "1=1";
		}
		String hql = new StringBuffer("from ").append(className).append(where == null ? "" : " where " + where).append(orderb == null ? "" : orderb).toString();
		try {
			return em.createQuery(hql).setFirstResult(start).setMaxResults(length).getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

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
	 *            排列条件,如"order by d2.appId " 排序也可以写到里面，如"order by d2.appId desc"
	 * @param start
	 *            起始值
	 * @param length
	 *            取多少条数据
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryListByWhere(final String swhere, final String className, String where, String orderb, final int start, final int length) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		if (StringUtils.isBlank(where)) {
			where = "1=1";
		}
		if (StringUtils.isBlank(orderb)) {
			orderb = null;
		}
		String hql = new StringBuffer("select ").append(swhere).append(" from ").append(className).append(where == null ? "" : " where " + where)
				.append(orderb == null ? "" : " " + "order by " + orderb).toString();
		try {
			return em.createQuery(hql).setFirstResult(start).setMaxResults(length).getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			e.printStackTrace();
			return null;
		} finally {
			hql = null;
		}
	}

	/**
	 * 
	 * @param sql
	 *            直接标准sql删除
	 * @throws Exception
	 */
	@Override
	public Boolean sqlDelete(String sql) throws DaoException {
		if (!StringUtils.isNotBlank(sql)) {
			throw new DaoException("sql不能为空或null");
		}
		try {
			em.createNativeQuery(sql).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(sql, e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param sql
	 *            直接标准sql查询
	 * @return
	 */
	@Override
	public Collection<?> sqlSelect(String sql) throws DaoException {
		if (StringUtils.isBlank(sql)) {
			throw new DaoException("sql不能为空或null");
		}
		try {
			return em.createNativeQuery(sql).getResultList();
		} catch (Exception e) {
			log.error(sql, e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param sql
	 *            直接标准sql更新
	 * @throws Exception
	 */
	@Override
	public Boolean sqlUpdate(String sql) throws DaoException {
		if (StringUtils.isBlank(sql)) {
			throw new DaoException("sql不能为空或null");
		}
		try {
			em.createNativeQuery(sql).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(sql, e);
			return false;
		}
	}

	/**
	 * 删除实体 *
	 * 
	 * @param entityids
	 *            实体id数组
	 */
	@Override
	@Transactional
	public Boolean delete(Serializable id) throws DaoException {
		if (StringUtils.isBlank((CharSequence) id)) {
			throw new DaoException("className不能为空或null");
		}
		try {
			em.remove(em.getReference(this.getEntityClass(), id));
			return true;
		} catch (Exception e) {
			log.error("delete" + id + ";", e);
			return false;
		}
	}

	@Transactional
	@Override
	public Boolean deleteByProperty(String propertyName, Object value) throws DaoException {
		if (!StringUtils.isNotBlank(propertyName)) {
			throw new DaoException("字段名为空");
		}
		String hql = new StringBuffer("delete  from ").append(getEntityClass().getSimpleName()).append(" where ").append(propertyName).append("='").append(value).append("'").toString();
		try {
			em.createQuery(hql).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(" propertyName:" + propertyName + ";value:" + value, e);
			return false;
		} finally {
			hql = null;
		}
	}

	@Transactional
	@Override
	public Boolean deleteByProperty(HashMap<String, Object> hashmap) throws DaoException {
		if (hashmap.isEmpty()) {
			throw new DaoException("参数列表为空");
		}

		StringBuffer hql = new StringBuffer("delete  from ").append(getEntityClass().getSimpleName()).append(" where 1=1 ");
		Iterator<?> it = hashmap.entrySet().iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			hql.append(" and ").append(entry.getKey().toString()).append("='").append(entry.getValue()).append("' ");
		}
		try {
			em.createQuery(hql.toString()).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error("deleteByProperty /hashmap:" + hashmap.toString(), e);
			return false;
		} finally {
			hql = null;
		}
	}

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体id
	 */
	@Transactional
	@Override
	public Boolean insert(BaseEntity BaseEntity) {
		try {
			em.persist(BaseEntity);
			em.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BaseEntity<?> load(String id) {
		if (id == null || "".equals(id)) {
			throw new DaoException("id不能为空或为null");
		}
		return load(id);
	}

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BaseEntity<?> load(int id) {
		return load(id);
	}

	/**
	 * 得一个实体
	 * 
	 * @param entity
	 *            实体id，如果没有返回NULL
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BaseEntity<?> load(long id) {
		return load(id);
	}

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return
	 */
	private BaseEntity<?> load(Object id) {
		return (BaseEntity<?>) em.find(getEntityClass(), id);
	}

	/**
	 * 更新实体
	 * 
	 * @param entity
	 *            实体id
	 */
	@Override
	@Transactional
	public Boolean update(BaseEntity BaseEntity) throws DaoException {
		if (BaseEntity == null || "".equals(BaseEntity)) {
			throw new DaoException("entity不能为空或为null");
		}
		try {
			em.merge(BaseEntity);
			em.flush();
			return true;
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			return false;
		}
	}

	/**
	 * 获取统计属性,该方法是为了解决hibernate解析联合主键select count(o) from Xxx
	 * o语句BUG而增加,hibernate对此jpql解析后的sql为select
	 * count(field1,field2,...),显示使用count()统计多个字段是错误的
	 * 
	 * @param <E>
	 * @param clazz
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	protected static <E> String getCountField(Class<E> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null && method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o." + propertydesc.getName() + "." + (!ps[1].getName().equals("class") ? ps[1].getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * 
	 * @param hql
	 * @throws Exception
	 */
	@Override
	public Boolean hqlDelete(String hql) throws DaoException {
		if (StringUtils.isBlank(hql)) {
			throw new DaoException("hql不能为空或为null");
		}
		try {
			em.createQuery(hql).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(hql, e);
			return false;
		}
	}

	/**
	 * 
	 * @param hql
	 * @throws Exception
	 */
	@Override
	public Boolean hqlUpdate(String hql) throws DaoException {
		if (StringUtils.isBlank(hql)) {
			throw new DaoException("hql不能为空或为null");
		}
		try {
			em.createQuery(hql).executeUpdate();
			return true;
		} catch (Exception e) {
			log.error(hql, e);
			return false;
		}
	}

	@Override
	public <T> T queryOneById(Class<T> entityClass, Object primaryKey) {
		try {
			return em.find(entityClass, primaryKey);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 根据class名和where条件查询对象list
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<?> queryList(String className, String where) throws DaoException {
		if (StringUtils.isBlank(className)) {
			throw new DaoException("className不能为空或null");
		}
		String hql = new StringBuffer("from ").append(className).append(" o ").append(StringUtils.isNotBlank(where) ? "where " + where : "").toString();
		try {
			Query query = em.createQuery(hql);
			return query.getResultList();
		} catch (Exception e) {
			log.error(hql, e);
			return null;
		} finally {
			hql = null;
		}
	}

	/**
	 * 根据class名询对象list
	 */
	public List<?> queryList(String className) throws DaoException {
		return queryList(className, null);
	}

}