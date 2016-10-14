package com.app.hibernate.persist.dao.impl;

import com.app.common.CollectionUtil;
import com.app.common.Logis;
import com.app.common.MapUtils;
import com.app.hibernate.persist.dao.HibernateDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.utils.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static com.app.common.Logis.fail;

/**
 * <p>
 * HibernateDao接口实现
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-14
 */
@Repository
public class HibernateDaoImpl<T> implements HibernateDao<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public T save(T t) {
		if (null == t)
			throw new AppHibernateException("execute Save Fail! Param is Empty !");
		HibernateUtil.getCurrentSession(sessionFactory).save(t);
		return t;
	}

	@Override
	public T get(Class<T> clazz, Serializable id) {
		if (null == id)
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		return (T) HibernateUtil.getCurrentSession(sessionFactory).get(clazz, id);
	}

	@Override
	public T get(String hql) {
		return (T) get(hql, Logis.EMPTY_MAP);
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		if (Logis.isBlank(hql))
			throw new AppHibernateException("execute Get Fail! Param is Empty !");
		T t = null;
		try {
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
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
		HibernateUtil.getCurrentSession(sessionFactory).delete(t);
	}

	@Override
	public void update(T t) {
		if (null == t)
			throw new AppHibernateException("execute Update! Param is Empty !");
		HibernateUtil.getCurrentSession(sessionFactory).merge(t);
	}

	@Override
	public void saveOrUpdate(T t) {
		if (null == t)
			throw new AppHibernateException("execute SaveOrUpdate! Param is Empty !");
		HibernateUtil.getCurrentSession(sessionFactory).saveOrUpdate(t);
	}

	@Override
	public List<T> query(String hql) {
		return query(hql, Logis.EMPTY_MAP);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		return query(hql, params, 0, 0);
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		if (Logis.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List<T> list = Logis.emptyList();
		try {
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
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
		return query(hql, Logis.EMPTY_MAP, page, rows);
	}

	@Override
	public void insertWithBatch(List<T> list) {
		if (CollectionUtil.isEmpty(list))
			throw new AppHibernateException("execute BatchInsert Fail! Param is Empty !");
		Session session = HibernateUtil.getCurrentSession(sessionFactory);
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
		Session session = HibernateUtil.getCurrentSession(sessionFactory);
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
		return query(clazz, page, rows, Logis.EMPTY_STRING, property, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String[] property, Object... value) {
		return query(clazz, 0, 0, Logis.EMPTY_STRING, property, value);
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
		List<T> list = Logis.emptyList();
		try {
			String hql = HibernateUtil.getListHql(order, clazz, property);
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
			if (null != value) {
				for (int i = 0; i < value.length; i++) {
					HibernateUtil.setParams(query, Logis.getString(i), value[i]);
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
		return query(clazz, Logis.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz, String order, int page, int rows) {
		return query(clazz, page, rows, Logis.EMPTY_MAP, order);
	}

	@Override
	public List<T> query(Class<T> clazz) {
		return query(clazz, Logis.EMPTY_MAP);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows) {
		return query(clazz, page, rows, Logis.EMPTY_MAP, null);
	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params) {
		return query(clazz, params, Logis.EMPTY_STRING);

	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> params, String order) {
		return query(clazz, 0, 0, params, order);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> params, String order) {
		List<T> list = Logis.emptyList();
		try {
			String hql = HibernateUtil.getListHql(order, clazz, params);
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
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
		return query(clazz, page, rows, map, Logis.EMPTY_STRING);
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
		return count(clazz, Logis.EMPTY_MAP);
	}

	@Override
	public long count(Class clazz, String property, Object... value) {
		return count(clazz, new String[] { property }, value);
	}

	@Override
	public long count(Class clazz, String[] property, Object... value) {
		String countHql = HibernateUtil.getCountHql(clazz, property);
		Query query = HibernateUtil.getHqlQuery(countHql, sessionFactory);
		for (int i = 0; i < value.length; i++) {
			HibernateUtil.setParams(query, Logis.getString(i), value[i]);
		}
		return (long) query.uniqueResult();
	}

	@Override
	public long count(Class clazz, Map<String, Object> params) {
		String hql = HibernateUtil.getCountHql(clazz, params);
		Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		return (long) query.uniqueResult();

	}
}
