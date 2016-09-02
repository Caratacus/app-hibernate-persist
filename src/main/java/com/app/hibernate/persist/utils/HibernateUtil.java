package com.app.hibernate.persist.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.app.common.LogisType;
import com.app.common.MapUtils;
import org.hibernate.Query;
import org.springframework.util.StringUtils;

/**
 * sql工具
 *
 * @author Caratacus
 * @version 1.0
 * @date 2016/9/1 0024
 */
public class HibernateUtil {

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
		StringBuilder builder = new StringBuilder(128);
		builder.append(" from ");
		builder.append(clazz.getSimpleName());
		return builder.toString();
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
		StringBuilder builder = new StringBuilder(128);
		builder.append(" from ");
		builder.append(clazz.getSimpleName());
		getOrderby(order, builder);
		return builder.toString();
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
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
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
	public static String getListHql(Class clazz, Map<String, Object> params) {
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
		getWhere(builder, params);
		return builder.toString();
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
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
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
		if (LogisType.isNoneBlank(property)) {
			builder.append(" where ");
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
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
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
		if (LogisType.isNotBlank(order)) {
			builder.append(" order by ");
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
		StringBuilder builder = new StringBuilder(128);
		builder.append("select count(*) from ");
		builder.append(clazz.getSimpleName());
		return builder.toString();
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
		StringBuilder builder = new StringBuilder(256);
		builder.append(getCountHql(clazz));
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
		StringBuilder builder = new StringBuilder(256);
		builder.append(getCountHql(clazz));
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
			builder.append(" where ");
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
		builder.append(" = ? and ");
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
			builder.append(" in ");
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
			builder.append(" in ");
		else
			builder.append(" = ");
		builder.append(":");
		builder.append(property);
		builder.append(" and ");
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
		if (0 != page && 0 != rows)
			query.setFirstResult((page - 1) * rows).setMaxResults(rows);
	}
}
