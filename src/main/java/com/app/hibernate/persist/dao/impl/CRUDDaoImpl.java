package com.app.hibernate.persist.dao.impl;

import com.app.common.Logis;
import com.app.common.MapUtils;
import com.app.hibernate.persist.dao.CRUDDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.utils.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static com.app.common.Logis.fail;

/**
 * <p>
 * CRUDDao接口实现
 * </p>
 *
 * @author Caratacus
 * @date 2016-10-14
 */
@Repository("crudDao")
public class CRUDDaoImpl implements CRUDDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public long queryCountWithHql(String hql) {
		return queryCountWithHql(hql, Logis.EMPTY_MAP);
	}

	@Override
	public long queryCountWithHql(String hql, Map<String, Object> params) {
		if (Logis.isBlank(hql))
			throw new AppHibernateException("Query Count Fail! Param is Empty !");
		Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		return (long) query.uniqueResult();
	}

	@Override
	public int executeHql(String hql) {
		return executeHql(hql, Logis.EMPTY_MAP);
	}

	@Override
	public int executeHql(String hql, Map<String, Object> params) {
		if (Logis.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int executeSql(String sql) {
		return executeSql(sql, Logis.EMPTY_MAP);
	}

	@Override
	public int executeSql(String sql, Map<String, Object> params) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public long queryCountWithSql(String sql) {
		return queryCountWithSql(sql, Logis.EMPTY_MAP);
	}

	@Override
	public long queryCountWithSql(String sql, Map<String, Object> params) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
		if (MapUtils.isNotEmpty(params)) {
			for (String key : params.keySet()) {
				Object obj = params.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.longValue();
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Map<String, Object> params) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Map resultMap = Logis.EMPTY_MAP;
		try {
			Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtil.setParams(query, key, obj);
				}
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return resultMap;
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql) {
		return queryMapWithSql(sql, Logis.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithSql(String sql) {
		return queryListWithSql(sql, Logis.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithSql(String sql, int page, int rows) {
		return queryListWithSql(sql, Logis.EMPTY_MAP, page, rows);
	}

	@Override
	public List<?> queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Logis.EMPTY_LIST;
		try {
			Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (MapUtils.isNotEmpty(params)) {
				for (String key : params.keySet()) {
					Object obj = params.get(key);
					HibernateUtil.setParams(query, key, obj);
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
	public List<?> queryListWithSql(String sql, Map<String, Object> params) {
		return queryListWithSql(sql, params, 0, 0);
	}

	@Override
	public int executeSqlUpdate(String sql) {
		return executeSqlUpdate(sql, Logis.EMPTY_MAP);
	}

	@Override
	public int executeSqlUpdate(String sql, Map<String, Object> params) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		if (Logis.isNotBlank(sql)) {
			try {
				Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
				if ((params != null) && !params.isEmpty()) {
					for (String key : params.keySet()) {
						Object obj = params.get(key);
						HibernateUtil.setParams(query, key, obj);
					}
				}
				resultCount = query.executeUpdate();
			} catch (Exception e) {
				logger.error(fail(), e);
			}
		}
		return resultCount;
	}

	@Override
	public List<?> queryListWithSql(String sql, Object[] args) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Logis.EMPTY_LIST;
		try {
			Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtil.setParams(query, Logis.getString(i), args[i]);
				}
			}
			list = query.list();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;
	}

	@Override
	public Map<?, ?> queryMapWithSql(String sql, Object[] args) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		Map resultMap = Logis.EMPTY_MAP;
		try {
			Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
			query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtil.setParams(query, Logis.getString(i), args[i]);
				}
			}
			resultMap = (Map) query.uniqueResult();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return resultMap;
	}

	@Override
	public int executeSqlUpdate(String sql, Object[] args) {
		if (Logis.isBlank(sql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		int resultCount = 0;
		try {
			Query query = HibernateUtil.getSqlQuery(sql, sessionFactory);
			if (null != args) {
				for (int i = 0; i < args.length; i++) {
					HibernateUtil.setParams(query, Logis.getString(i), args[i]);
				}
			}
			resultCount = query.executeUpdate();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return resultCount;
	}

	@Override
	public List<?> queryListWithHql(Class clazz) {
		return queryListWithHql(clazz, Logis.EMPTY_MAP);
	}

	@Override
	public List<?> queryListWithHql(Class clazz, String property, Object value) {
		return queryListWithHql(clazz, new String[] { property }, value);
	}

	@Override
	public Object queryMapWithHql(Class clazz, String property, Object value) {
		Object object = null;
		try {
			String hql = HibernateUtil.getListHql(clazz, property);
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
			HibernateUtil.setParams(query, "0", value);
			object = query.uniqueResult();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return object;
	}

	@Override
	public List<?> queryListWithHql(Class clazz, String[] property, Object... value) {
		List list = Logis.EMPTY_LIST;
		try {
			String hql = HibernateUtil.getListHql(clazz, property);
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
			if (null != value) {
				for (int i = 0; i < value.length; i++) {
					HibernateUtil.setParams(query, Logis.getString(i), value);
				}
			}
			list = query.list();

		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;

	}

	@Override
	public List<?> queryListWithHql(Class clazz, Map<String, Object> map) {
		List list = Logis.EMPTY_LIST;
		try {
			String hql = HibernateUtil.getListHql(clazz, map);
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				HibernateUtil.setParams(query, key, obj);
			}
			list = query.list();
		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;

	}

	@Override
	public List<?> queryListWithHql(String hql) {
		return queryListWithHql(hql, 0, 0);
	}

	@Override
	public List<?> queryListWithHql(String hql, int page, int rows) {
		if (Logis.isBlank(hql))
			throw new AppHibernateException("execute Query Fail! Param is Empty !");
		List list = Logis.EMPTY_LIST;
		try {
			Query query = HibernateUtil.getHqlQuery(hql, sessionFactory);
			HibernateUtil.setPage(page, rows, query);
			list = query.list();

		} catch (Exception e) {
			logger.error(fail(), e);
		}
		return list;
	}

}
