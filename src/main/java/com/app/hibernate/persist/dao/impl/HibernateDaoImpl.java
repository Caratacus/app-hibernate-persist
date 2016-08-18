package com.app.hibernate.persist.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.hibernate.persist.dao.HibernateDao;
import com.app.hibernate.persist.utils.SqlUtil;

@SuppressWarnings("unchecked")
@Repository
public class HibernateDaoImpl<T> implements HibernateDao<T> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * 获得当前事物的session
	 *
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public T save(T o) {

		if (o != null) {
			this.getCurrentSession().save(o);
		}
		return o;
	}

	@Override
	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	@Override
	public T get(String hql) {
		Query q = getHqlQuery(hql);
		List<T> l = q.list();
		if ((l != null) && (l.size() > 0)) {
			return l.get(0);
		}
		return null;
	}

	/**
	 * 获取HQLQuery
	 *
	 * @param hql
	 * @return
	 *
	 */
	private Query getHqlQuery(String hql) {
		return this.getCurrentSession().createQuery(hql);
	}

	/**
	 * 获取SQLQuery
	 *
	 * @param sql
	 * @return
	 *
	 */
	private Query getSqlQuery(String sql) {
		return this.getCurrentSession().createSQLQuery(sql);
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if ((l != null) && (l.size() > 0)) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public void delete(T o) {
		if (o != null) {
			this.getCurrentSession().delete(o);
		}
	}

	@Override
	public void update(T o) {
		if (o != null) {
			this.getCurrentSession().merge(o);
		}
	}

	@Override
	public void saveOrUpdate(T o) {
		if (o != null) {
			this.getCurrentSession().saveOrUpdate(o);
		}
	}

	@Override
	public List<T> query(String hql) {
		Query q = getHqlQuery(hql);
		return q.list();
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@Override
	public List<T> query(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getHqlQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List<T> query(String hql, int page, int rows) {
		Query q = getHqlQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public void insertWithBatch(List<T> list) {
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
	public List<T> query(Class<T> clazz) {
		String hql = SqlUtil.getListHql(clazz);
		List<T> result = this.getHqlQuery(hql).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows) {
		String hql = SqlUtil.getListHql(clazz);
		List<T> result = this.getHqlQuery(hql).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, String property, Object value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		List<T> result = query.list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, String[] property, Object... value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		List<T> result = query.list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> map) {
		List<String> keys = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		for (String key : map.keySet()) {
			keys.add(key);
			values.add(map.get(key));
		}

		String[] condition = new String[keys.size()];
		keys.toArray(condition);
		Object[] value = new Object[values.size()];
		values.toArray(value);
		return query(clazz, condition, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String property, Object value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		List<T> result = query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String[] property, Object... value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		List<T> result = query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map) {
		List<String> keys = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		for (String key : map.keySet()) {
			keys.add(key);
			values.add(map.get(key));
		}

		String[] condition = new String[keys.size()];
		keys.toArray(condition);
		Object[] value = new Object[values.size()];
		values.toArray(value);
		return query(clazz, page, rows, condition, value);
	}

	@Override
	public List<T> query(Class<T> clazz, String order) {
		String hql = SqlUtil.getListHql(order, clazz);
		List<T> result = this.getHqlQuery(hql).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, String order, int page, int rows) {
		String hql = SqlUtil.getListHql(order, clazz);
		List<T> result = this.getHqlQuery(hql).setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String property, Object value) {
		String hql = SqlUtil.getListHql(order, clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		List<T> result = query.list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, String order, String[] property, Object... value) {
		String hql = SqlUtil.getListHql(order, clazz, property);
		Query query = getHqlQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		List<T> result = query.list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, Map<String, Object> map, String order) {
		List<String> keys = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		for (String key : map.keySet()) {
			keys.add(key);
			values.add(map.get(key));
		}

		String[] condition = new String[keys.size()];
		keys.toArray(condition);
		Object[] value = new Object[values.size()];
		values.toArray(value);
		return query(clazz, order, condition, value);
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String property, Object value) {
		String hql = SqlUtil.getListHql(order, clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		List<T> result = query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, String order, String[] property, Object... value) {
		String hql = SqlUtil.getListHql(order, clazz, property);
		Query query = getHqlQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		List<T> result = query.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		return result;
	}

	@Override
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map, String order) {
		List<String> keys = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		for (String key : map.keySet()) {
			keys.add(key);
			values.add(map.get(key));
		}

		String[] condition = new String[keys.size()];
		keys.toArray(condition);
		Object[] value = new Object[values.size()];
		values.toArray(value);
		return query(clazz, page, rows, order, condition, value);
	}

	@Override
	public long count(Class clazz) {
		String countHql = SqlUtil.getCountHql(clazz);
		Query query = getHqlQuery(countHql);
		Long count = (long) query.uniqueResult();
		return count;
	}

	@Override
	public long count(Class clazz, String property, Object... value) {
		String countHql = SqlUtil.getCountHql(clazz, property);
		Query query = getHqlQuery(countHql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public long count(Class clazz, String[] property, Object... value) {
		String countHql = SqlUtil.getCountHql(clazz, property);
		Query query = getHqlQuery(countHql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		long count = (long) query.uniqueResult();
		return count;
	}

	@Override
	public long count(Class clazz, Map<String, Object> map) {
		List<String> keys = new ArrayList<String>();
		List<Object> values = new ArrayList<Object>();

		for (String key : map.keySet()) {
			keys.add(key);
			values.add(map.get(key));
		}

		String[] condition = new String[keys.size()];
		keys.toArray(condition);
		Object[] value = new Object[values.size()];
		values.toArray(value);
		return count(clazz, condition, value);
	}
}
