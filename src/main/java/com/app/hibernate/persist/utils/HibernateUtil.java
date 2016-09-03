package com.app.hibernate.persist.utils;

import com.app.common.Logis;
import com.app.common.MapUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * sql工具
 *
 * @author Caratacus
 * @version 1.0
 * @date 2016/9/1 0024
 */
public class HibernateUtil {
    private static final String BASE_COUNT = "SELECT COUNT(0) FROM ";
    private static final String BASE_LIST = " FROM ";

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(Class clazz) {
        return getListHql(clazz, Logis.EMPTY_MAP);
    }

    /**
     * BASE LIST HQL
     *
     * @param clazz
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static StringBuilder getBaseListHql(Class clazz) {
        return getBaseHql(clazz, BASE_LIST);
    }

    /**
     * 基本的hql
     *
     * @param clazz
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static StringBuilder getBaseHql(Class clazz, String baseHql) {
        StringBuilder builder = new StringBuilder(baseHql);
        builder.append(clazz.getSimpleName());
        return builder;
    }

    /**
     * 生成当前对象的HQL
     *
     * @param order
     * @param clazz
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(String order, Class clazz) {
        return getListHql(order, clazz, Logis.EMPTY_MAP);
    }

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @param property
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(Class clazz, String... property) {
        return getListHql(Logis.EMPTY_STRING, clazz, property);
    }

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @param params
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(Class clazz, Map<String, Object> params) {
        return getListHql(Logis.EMPTY_STRING, clazz, params);
    }

    /**
     * 生成当前对象的HQL
     *
     * @param order
     * @param clazz
     * @param property
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(String order, Class clazz, String... property) {
        StringBuilder builder = getBaseListHql(clazz);
        getWhere(builder, property);
        getOrderby(order, builder);
        return builder.toString();
    }

    /**
     * 生成HQL WHERE SQL
     *
     * @param builder
     * @param property
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void getWhere(StringBuilder builder, String... property) {
        if (Logis.isNoneBlank(property)) {
            builder.append(" WHERE ");
            for (int i = 0; i < property.length; i++) {
                builder.append(property[i]);
                if (i == property.length - 1) {
                    setByArray(builder, property[i]);
                } else {
                    setAndByArray(builder, property[i]);
                }
            }
        }
    }

    /**
     * 生成当前对象的HQL
     *
     * @param order
     * @param clazz
     * @param params
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getListHql(String order, Class clazz, Map<String, Object> params) {
        StringBuilder builder = getBaseListHql(clazz);
        getWhere(builder, params);
        getOrderby(order, builder);
        return builder.toString();
    }

    /**
     * 生成HQL ORDER BY SQL
     *
     * @param builder
     * @param builder
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void getOrderby(String order, StringBuilder builder) {
        if (Logis.isNotBlank(order)) {
            builder.append(" ORDER BY ");
            builder.append(order);
        }
    }

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getCountHql(Class clazz) {
        return getCountHql(clazz, Logis.EMPTY_MAP);
    }

    /**
     * 简单的count hql
     *
     * @param clazz
     * @return StringBuilder
     * @throws
     * @author Caratacus
     * @date 2016/9/3 0003
     * @version 1.0
     */
    private static StringBuilder getBaseCountHql(Class clazz) {
        return getBaseHql(clazz, BASE_COUNT);
    }

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @param property
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getCountHql(Class clazz, String... property) {
        StringBuilder builder = getBaseCountHql(clazz);
        getWhere(builder, property);
        return builder.toString();
    }

    /**
     * 生成当前对象的HQL
     *
     * @param clazz
     * @param params
     * @return String
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static String getCountHql(Class clazz, Map<String, Object> params) {
        StringBuilder builder = getBaseCountHql(clazz);
        getWhere(builder, params);
        return builder.toString();
    }

    /**
     * 生成HQL WHERE SQL
     *
     * @param builder
     * @param params
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void getWhere(StringBuilder builder, Map<String, Object> params) {
        if (MapUtils.isNotEmpty(params)) {
            List<String> list = new ArrayList<String>();
            list.addAll(params.keySet());
            builder.append(" WHERE ");
            for (int i = 0; i < list.size(); i++) {
                String property = list.get(i);
                Object value = params.get(property);
                builder.append(property);
                if (i == list.size() - 1) {
                    setByMap(builder, property, value);
                } else {
                    setAndByMap(builder, property, value);
                }
            }
        }
    }

    /**
     * sql语句连接
     *
     * @param builder
     * @param property
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void setByArray(StringBuilder builder, String property) {
        builder.append(" = ?");
    }

    /**
     * sql语句连接And
     *
     * @param builder
     * @param property
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void setAndByArray(StringBuilder builder, String property) {
        builder.append(" = ? AND ");
    }

    /**
     * sql语句连接
     *
     * @param builder
     * @param property
     * @param object
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void setByMap(StringBuilder builder, String property, Object object) {
        if (object.getClass().isArray() || object instanceof List)
            builder.append(" IN ");
        else
            builder.append(" = ");
        builder.append(":");
        builder.append(property);
    }

    /**
     * sql语句连接And
     *
     * @param builder
     * @param property
     * @param object
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    private static void setAndByMap(StringBuilder builder, String property, Object object) {
        if (object.getClass().isArray() || object instanceof List)
            builder.append(" IN ");
        else
            builder.append(" = ");
        builder.append(":");
        builder.append(property);
        builder.append(" AND ");
    }

    /**
     * 设置hibernate参数
     *
     * @param query
     * @param key
     * @param obj
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static void setParams(Query query, String key, Object obj) {
        if (obj.getClass().isArray()) {
            query.setParameterList(key, (Object[]) obj);
        } else if (obj instanceof List) {
            query.setParameterList(key, (List) obj);
        } else {
            query.setParameter(key, obj);
        }
    }

    /**
     * 设置分页
     *
     * @param page
     * @param rows
     * @param query
     * @return
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static void setPage(int page, int rows, Query query) {
        if (0 != rows) {
            // 只判断row , 如果page异常 初始化为1
            page = Logis.getInteger(page, 1);
            query.setFirstResult((page - 1) * rows).setMaxResults(rows);
        }
    }

    /**
     * 获取SQLQuery对象
     *
     * @param sql
     * @param factory
     * @return Query
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static Query getSqlQuery(String sql, SessionFactory factory) {
        System.err.println("Execute SQL：" + sql);
        return getCurrentSession(factory).createSQLQuery(sql);
    }

    /**
     * 获取HQLQuery对象
     *
     * @param hql
     * @param factory
     * @return Query
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static Query getHqlQuery(String hql, SessionFactory factory) {
        System.err.println("Execute HQL：" + hql);
        return getCurrentSession(factory).createQuery(hql);
    }

    /**
     * 获得当前事务的session
     *
     * @param factory
     * @return Session
     * @throws
     * @author Caratacus
     * @date 2016/9/2 0002
     * @version 1.0
     */
    public static Session getCurrentSession(SessionFactory factory) {
        return factory.getCurrentSession();
    }

}
