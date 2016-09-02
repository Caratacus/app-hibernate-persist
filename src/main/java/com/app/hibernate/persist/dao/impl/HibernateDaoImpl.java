package com.app.hibernate.persist.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.app.common.CollectionUtil;
import com.app.common.MapUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.KeyFactory;
import org.springframework.stereotype.Repository;

import com.app.common.LogisType;
import com.app.hibernate.persist.dao.HibernateDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.utils.HibernateUtil;

import static com.app.common.Common.fail;

@SuppressWarnings("unchecked")
@Repository
public class HibernateDaoImpl<T> implements HibernateDao<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事务的session
	 *
	 * @param
	 * @return Session
	 * @throws
	 * @author Caratacus
	 * @date 2016/9/2 0002
	 * @version 1.0
	 */
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public T save(T t) {
		if (null == t)
			throw new AppHibernateException("execute Save Fail! Param is Empty !");
		this.getCurrentSession().save(t);
		return t;
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		if (null == id)
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		return (T) this.getCurrentSession().get(clazz, id);
	}

	@Override
	public T get(String hql) {
		return (T) get(hql, Collections.EMPTY_MAP);
	}

	/**
	 * 获取SQLQuery对象
	 *
	 * @param sql
	 * @return Query
	 * @throws
	 * @author Caratacus
	 * @date 2016/9/2 0002
	 * @version 1.0
	 */
	private Query getSqlQuery(String sql) {
		System.err.println("Execute SQL：" + sql);
		return this.getCurrentSession().createSQLQuery(sql);
	}

	/**
	 * 获取HQLQuery对象
	 *
	 * @param hql
	 * @return Query
	 * @throws
	 * @author Caratacus
	 * @date 2016/9/2 0002
	 * @version 1.0
	 */
	private Query getHqlQuery(String hql) {
		System.err.println("Execute HQL：" + hql);
		return this.getCurrentSession().createQuery(hql);
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		if (LogisType.isBlank(hql))
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		T t = null;
		try {
			Query query = getHqlQuery(hql);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtil.setParams(query, key, obj);
				}
			}
			t = (T) query.uniqueResult();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return t;

	}

	@Override
	public void delete(T t) {
		if (null == t)
			throw new AppHibernateException("execute Delete! Param is Empty !");
		this.getCurrentSession().delete(t);
	}

	@Override
	public void update(T t) {
		if (null == t)
			throw new AppHibernateException("execute Update! Param is Empty !");
		this.getCurrentSession().merge(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		if (null == t)
			throw new AppHibernateException("execute SaveOrUpdate! Param is Empty !");
		this.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public List<T> query(String hql) {
		return query(hql, Collections.EMPTY_MAP);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		return query(hql, params, 0, 0);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		if (LogisType.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List<T> list = Collections.EMPTY_LIST;
		try {
			Query query = getHqlQuery(hql);
			setParamMap(params, query);
			HibernateUtil.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;

	}

	@Override
	public List<T> query(String hql, int page, int rows) {
		return query(hql, Collections.EMPTY_MAP, page, rows);
	}

	@Override
	public void insertWithBatch(List<T> list) {
		if (CollectionUtil.isEmpty(list))
			throw new AppHibernateException("execute BatchInsert Fail! Param is Empty !");
		Session session = this.getCurrentSession();
		for (int i = 0; i < list.size(); i++) {
			session.save(list.get(i));
			if (i % 30 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public void updateWithBatch(List<T> list) {
		if (CollectionUtil.isEmpty(list))
			throw new AppHibernateException("execute BatchUpdate Fail! Param is Empty !");
		Session session = this.getCurrentSession();
		for (int i = 0; i < list.size(); i++) {
			session.update(list.get(i));
			if (i % 30 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	@Override
	public List<T> query(Class<T> clazz, String property, Object value) {
		return query(clazz, 0, 0, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String[] property, Object... value) {

		return query(clazz, 0, 0, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String property, Object value) {
		return query(clazz, page, rows, null, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String[] property, Object... value) {
		return query(clazz, 0, 0, null, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String property, Object value) {
		return query(clazz, 0, 0, order, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String[] property, Object... value) {
		return query(clazz, 0, 0, order, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String property, Object value) {
		return query(clazz, page, rows, order, new String[] { property }, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String[] property, Object... value) {
		List<T> list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtil.getListHql(order, clazz, property);
			Query query = getHqlQuery(hql);
			if (null != value) {
				for (int i = 0; i < value.length; i++) {
					query.setParameter(i, value[i]);
				}
			}
			HibernateUtil.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;
	}

	@Override
	public List<T> query(Class<T> clazz, String order) {
		return query(clazz, Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, int page, int rows) {
		return query(clazz, page, rows, Collections.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz) {
		return query(clazz, Collections.EMPTY_MAP);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows) {
		return query(clazz, page, rows, Collections.EMPTY_MAP, null);
	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params) {

		return query(clazz, params, null);

	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params, String order) {
		return query(clazz, 0, 0, params, order);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> params, String order) {
		List<T> list = Collections.EMPTY_LIST;
		try {
			String hql = HibernateUtil.getListHql(order, clazz, params);
			Query query = getHqlQuery(hql);
			setParamMap(params, query);
			HibernateUtil.setPage(page, rows, query);
			list = query.list();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;

	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map) {
		return query(clazz, page, rows, map, null);
	}

	/**
	 * Query设置Map参数
	 * 
	 * @param params
	 * @param query
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/9/2 0002
	 * @version 1.0
	 */
	private void setParamMap(Map<String, Object> params, Query query) {
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
	}

	@Override
	public long count(Class clazz) {
		return count(clazz, Collections.EMPTY_MAP);
	}

	@Override
	public long count(Class clazz, String property, Object... value) {
		return count(clazz, new String[] { property }, value);
	}

	@Override
	public long count(Class clazz, String[] property, Object... value) {
		String countHql = HibernateUtil.getCountHql(clazz, property);
		Query query = getHqlQuery(countHql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		return (long) query.uniqueResult();
	}

	@Override
	public long count(Class clazz, Map<String, Object> params) {

		String hql = HibernateUtil.getListHql(clazz, params);
		Query query = getHqlQuery(hql);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		return (long) query.uniqueResult();

	}
}
