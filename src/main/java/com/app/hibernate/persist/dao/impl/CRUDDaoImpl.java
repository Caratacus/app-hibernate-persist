package com.app.hibernate.persist.dao.impl;

import static com.app.common.Common.fail;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.common.LogisType;
import com.app.common.MapUtils;
import com.app.hibernate.persist.dao.CRUDDao;
import com.app.hibernate.persist.exceptions.AppHibernateException;
import com.app.hibernate.persist.utils.HibernateUtil;

@Repository("crudDao")
public class CRUDDaoImpl implements CRUDDao {

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
    private Session getCurrentSession() {
        return this.sessionFactory.getCurrentSession();
    }

    @Override
    public long queryCountWithHql(String hql) {
        return queryCountWithHql(hql, Collections.EMPTY_MAP);
    }

    @Override
    public long queryCountWithHql(String hql, Map<String, Object> params) {
        if (LogisType.isBlank(hql))
            throw new AppHibernateException("Query Count Fail! Param is Empty !");
        Query query = getHqlQuery(hql);
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
        return executeHql(hql, Collections.EMPTY_MAP);
    }

    @Override
    public int executeHql(String hql, Map<String, Object> params) {
        if (LogisType.isBlank(hql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        Query query = getHqlQuery(hql);
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
        return executeSql(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int executeSql(String sql, Map<String, Object> params) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        Query query = getSqlQuery(sql);
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
        return queryCountWithSql(sql, Collections.EMPTY_MAP);
    }

    @Override
    public long queryCountWithSql(String sql, Map<String, Object> params) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        Query query = getSqlQuery(sql);
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
    public Map queryMapWithSql(String sql, Map<String, Object> params) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        Map resultMap = Collections.EMPTY_MAP;
        try {
            Query query = getSqlQuery(sql);
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
    public Map queryMapWithSql(String sql) {
        return queryMapWithSql(sql, Collections.EMPTY_MAP);
    }

    @Override
    public List queryListWithSql(String sql) {
        return queryListWithSql(sql, Collections.EMPTY_MAP);
    }

    @Override
    public List queryListWithSql(String sql, int page, int rows) {
        return queryListWithSql(sql, Collections.EMPTY_MAP, page, rows);
    }

    @Override
    public List queryListWithSql(String sql, Map<String, Object> params, int page, int rows) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        List list = Collections.EMPTY_LIST;
        try {
            Query query = getSqlQuery(sql);
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
    public List queryListWithSql(String sql, Map<String, Object> params) {
        return queryListWithSql(sql, params, 0, 0);
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
    public int executeSqlUpdate(String sql) {
        return executeSqlUpdate(sql, Collections.EMPTY_MAP);
    }

    @Override
    public int executeSqlUpdate(String sql, Map<String, Object> params) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        int resultCount = 0;
        if (LogisType.isNotBlank(sql)) {
            try {
                Query query = getSqlQuery(sql);
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
    public List queryListWithSql(String sql, Object[] args) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        List list = Collections.EMPTY_LIST;
        try {
            Query query = getSqlQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            if (null != args) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
            }
            list = query.list();
        } catch (Exception e) {
            logger.error(fail(), e);
        }
        return list;
    }

    @Override
    public Map queryMapWithSql(String sql, Object[] args) {
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        Map resultMap = Collections.EMPTY_MAP;
        try {
            Query query = getSqlQuery(sql);
            query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
            if (null != args) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
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
        if (LogisType.isBlank(sql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        int resultCount = 0;
        try {
            Query query = getSqlQuery(sql);
            if (null != args) {
                for (int i = 0; i < args.length; i++) {
                    query.setParameter(i, args[i]);
                }
            }
            resultCount = query.executeUpdate();
        } catch (Exception e) {
            logger.error(fail(), e);
        }
        return resultCount;
    }

    @Override
    public List queryListWithHql(Class clazz) {
        List list = Collections.EMPTY_LIST;
        return queryListWithHql(clazz, Collections.EMPTY_MAP);
    }

    @Override
    public List queryListWithHql(Class clazz, String property, Object value) {
        return queryListWithHql(clazz, new String[]{property}, value);
    }

    @Override
    public Object queryMapWithHql(Class clazz, String property, Object value) {
        Object object = null;
        try {
            String hql = HibernateUtil.getListHql(clazz, property);
            Query query = getHqlQuery(hql);
            query.setParameter(0, value);
            object = query.uniqueResult();
        } catch (Exception e) {
            logger.error(fail(), e);
        }
        return object;
    }

    @Override
    public List queryListWithHql(Class clazz, String[] property, Object... value) {
        List list = Collections.EMPTY_LIST;
        try {
            String hql = HibernateUtil.getListHql(clazz, property);
            Query query = getHqlQuery(hql);
            if (null != value) {
                for (int i = 0; i < value.length; i++) {
                    query.setParameter(i, value[i]);
                }
            }
            list = query.list();

        } catch (Exception e) {
            logger.error(fail(), e);
        }
        return list;

    }

    @Override
    public List queryListWithHql(Class clazz, Map<String, Object> map) {
        List list = Collections.EMPTY_LIST;
        try {
            String hql = HibernateUtil.getListHql(clazz, map);
            Query query = getHqlQuery(hql);
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
    public List queryListWithHql(String hql) {
        return queryListWithHql(hql, 0, 0);
    }

    @Override
    public List queryListWithHql(String hql, int page, int rows) {
        if (LogisType.isBlank(hql))
            throw new AppHibernateException("execute Query Fail! Param is Empty !");
        List list = Collections.EMPTY_LIST;
        try {
            Query query = getHqlQuery(hql);
            HibernateUtil.setPage(page, rows, query);
            list = query.list();

        } catch (Exception e) {
            logger.error(fail(), e);
        }
        return list;

    }


}
