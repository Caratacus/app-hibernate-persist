package com.app.hibernate.persist.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.hibernate.persist.dao.CRUDDao;
import com.app.hibernate.persist.utils.SqlUtil;

@Repository("crudDao")
public class CRUDDaoImpl implements CRUDDao {

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
	public long queryCountWithHql(String hql) {
		Query q = getHqlQuery(hql);
		return (long) q.uniqueResult();
	}

	@Override
	public long queryCountWithHql(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj.getClass().isArray()) {
					q.setParameterList(key, (String[]) obj);
				} else if (obj instanceof List) {
					q.setParameterList(key, (List) obj);
				} else {
					q.setParameter(key, params.get(key));
				}
			}
		}
		return (long) q.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		Query q = getHqlQuery(hql);
		return q.executeUpdate();
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getHqlQuery(hql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj.getClass().isArray()) {
					q.setParameterList(key, (String[]) obj);
				} else if (obj instanceof List) {
					q.setParameterList(key, (List) obj);
				} else {
					q.setParameter(key, params.get(key));
				}
			}
		}
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql) {
		Query q = getSqlQuery(sql);
		return q.executeUpdate();
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		Query q = getSqlQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj.getClass().isArray()) {
					q.setParameterList(key, (String[]) obj);
				} else if (obj instanceof List) {
					q.setParameterList(key, (List) obj);
				} else {
					q.setParameter(key, params.get(key));
				}
			}
		}
		return q.executeUpdate();
	}

	@Override
	public long queryCountWithSql(String sql) {
		Query q = getSqlQuery(sql);
		BigInteger count = (BigInteger) q.uniqueResult();
		return count.longValue();
	}

	@Override
	public long queryCountWithSql(String sql, Map<String, Object> params) {
		Query q = getSqlQuery(sql);
		if ((params != null) && !params.isEmpty()) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				if (obj.getClass().isArray()) {
					q.setParameterList(key, (String[]) obj);
				} else if (obj instanceof List) {
					q.setParameterList(key, (List) obj);
				} else {
					q.setParameter(key, params.get(key));
				}
			}
		}
		BigInteger count = (BigInteger) q.uniqueResult();
		return count.longValue();
	}

	@Override
	public List queryListWithSql(String sql) {
		List list = null;
		try {
			logger.debug(sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			logger.error("queryListWithSql", e);
		}
		return list;
	}

	@Override
	public Map queryMapWithSql(String sql) {
		Map resultMap = null;
		try {
			logger.debug(sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.error("queryMapWithSql", e);
		}
		return resultMap;
	}

	@Override
	public List queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		List list = null;
		try {
			logger.debug("params", params);
			logger.debug(sql);
			Query q = getSqlQuery(sql);
			q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if ((params != null) && !params.isEmpty()) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					if (obj.getClass().isArray()) {
						q.setParameterList(key, (String[]) obj);
					} else if (obj instanceof List) {
						q.setParameterList(key, (List) obj);
					} else {
						q.setParameter(key, params.get(key));
					}
				}
			}
			list = q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch (Exception e) {
			logger.error("queryListWithSql", e);
		}
		return list;
	}

	@Override
	public List queryListWithSql(String sql, Map<String, Object> params) {
		List list = null;
		try {
			logger.debug("params", params);
			logger.debug("sql", sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if ((params != null) && !params.isEmpty()) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					if (obj.getClass().isArray()) {
						query.setParameterList(key, (String[]) obj);
					} else if (obj instanceof List) {
						query.setParameterList(key, (List) obj);
					} else {
						query.setParameter(key, params.get(key));
					}
				}
			}
			list = query.list();
		} catch (Exception e) {
			logger.error("queryListWithSql", e);
		}
		return list;
	}

	@Override
	public Map queryMapWithSql(String sql, Map<String, Object> params) {
		Map resultMap = null;
		try {
			logger.debug("params", params);
			logger.debug("sql", sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if ((params != null) && !params.isEmpty()) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					if (obj.getClass().isArray()) {
						query.setParameterList(key, (String[]) obj);
					} else if (obj instanceof List) {
						query.setParameterList(key, (List) obj);
					} else {
						query.setParameter(key, params.get(key));
					}
				}
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.error("queryMapWithSql", e);
		}
		return resultMap;
	}

	/**
	 * 获取SQLQuery对象
	 *
	 * @param sql
	 * @return
	 * @author Cancer
	 */
	private Query getSqlQuery(String sql) {
		return this.getCurrentSession().createSQLQuery(sql);
	}

	/**
	 * 获取HQLQuery对象
	 *
	 * @param hql
	 * @return
	 * @author Cancer
	 */
	private Query getHqlQuery(String hql) {
		return this.getCurrentSession().createQuery(hql);
	}

	@Override
	public int executeSqlUpdate(String sql) {
		int resultCount = 0;
		if (null != sql) {
			try {
				logger.debug(sql);
				Query query = getSqlQuery(sql);
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.error("executeSqlUpdate", e);
			}
		}
		return resultCount;
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> params) {
		int resultCount = 0;
		if (null != sql) {
			try {
				logger.debug("params", params);
				logger.debug("sql", sql);
				Query query = getSqlQuery(sql);
				if ((params != null) && !params.isEmpty()) {
					for (String key : params.keySet()) {
						Object obj = params.get(key);
						if (obj.getClass().isArray()) {
							query.setParameterList(key, (String[]) obj);
						} else if (obj instanceof List) {
							query.setParameterList(key, (List) obj);
						} else {
							query.setParameter(key, params.get(key));
						}
					}
				}
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.error("executeSqlUpdate", e);
			}
		}
		return resultCount;
	}

	@Override
	public List queryListWithSql(String sql, Object[] args) {
		List list = null;
		try {
			logger.debug("params", args);
			logger.debug("sql", sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			list = query.list();
		} catch (Exception e) {
			logger.error("queryListWithSql", e);
		}
		return list;
	}

	@Override
	public Map queryMapWithSql(String sql, Object[] args) {
		Map resultMap = null;
		try {
			logger.debug("params", args);
			logger.debug("sql", sql);
			Query query = getSqlQuery(sql);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.error("queryMapWithSql", e);
		}
		return resultMap;
	}

	@Override
	public int executeSqlUpdate(String sql, Object[] args) {
		int resultCount = 0;
		if (null != sql) {
			try {
				logger.debug("params", args);
				logger.debug("sql", sql);
				Query query = getSqlQuery(sql);
				for (int i = 0; i < args.length; i++) {
					query.setParameter(i, args[i]);
				}
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.error("executeSqlUpdate", e);
			}
		}
		return resultCount;
	}

	@Override
	public List queryListWithHql(Class clazz) {
		String hql = SqlUtil.getListHql(clazz);
		Query query = getHqlQuery(hql);
		return query.list();
	}

	@Override
	public List queryListWithHql(Class clazz, String property, Object value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		return query.list();
	}

	@Override
	public Object queryMapWithHql(Class clazz, String property, Object value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		query.setParameter(0, value);
		return query.uniqueResult();
	}

	@Override
	public List<Map> queryListWithHql(String hql) {
		Query query = getHqlQuery(hql);
		return (List<Map>)query.list();
	}

	@Override
	public List queryListWithHql(Class clazz, String[] property, Object... value) {
		String hql = SqlUtil.getListHql(clazz, property);
		Query query = getHqlQuery(hql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		return query.list();
	}

	@Override
	public List queryListWithHql(Class clazz, Map<String, Object> map) {
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
		return queryListWithHql(clazz, condition, value);
	}

	@Override
	public long queryCountWithHql(Class clazz) {
		String countHql = SqlUtil.getCountHql(clazz);
		Query query = getHqlQuery(countHql);
		Long count = (long) query.uniqueResult();
		return count;
	}

	@Override
	public long queryCountWithHql(Class clazz, String property, Object... value) {
		String countHql = SqlUtil.getCountHql(clazz, property);
		Query query = getHqlQuery(countHql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		Long count = (Long) query.uniqueResult();
		return count;
	}

	@Override
	public long queryCountWithHql(Class clazz, String[] property, Object... value) {
		String countHql = SqlUtil.getCountHql(clazz, property);
		Query query = getHqlQuery(countHql);
		for (int i = 0; i < value.length; i++) {
			query.setParameter(i, value[i]);
		}
		long count = (long) query.uniqueResult();
		return count;
	}

	@Override
	public long queryCountWithHql(Class clazz, Map<String, Object> map) {
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
		return queryCountWithHql(clazz, condition, value);
	}

	@Override
	public List queryListWithSql(String sql, int page, int rows) {
		List list = null;
		try {
			logger.debug(sql);
			Query q = getSqlQuery(sql);
			q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			list = q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
		} catch (Exception e) {
			logger.error("queryListWithSql", e);
		}
		return list;
	}

}
