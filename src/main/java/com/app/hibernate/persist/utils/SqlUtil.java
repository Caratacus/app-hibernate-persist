package com.app.hibernate.persist.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * sql工具
 * 
 * @author zym
 *
 */
public class SqlUtil {
	/**
	 * 将map转成sql语句
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static Map<String, Object> parseMapInsert(String tableName, Map<String, Object> map) {
		if (tableName == null || tableName.equals("") || map == null || map.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer key = new StringBuffer("");
		StringBuffer value = new StringBuffer("");
		Object[] valueList = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> en : map.entrySet()) {
			if (en.getKey() != null && en.getValue() != null && !en.getKey().toString().equals("")
					&& !en.getValue().toString().equals("")) {
				if ("INDEX".equals(en.getKey())) {
					key.append("\"");
					key.append(en.getKey().toString().replace("$DATE", ""));
					key.append("\"");
				} else {
					key.append(en.getKey().toString().replace("$DATE", ""));
				}
				key.append(",");
				// if (en.getKey().toString().contains("$DATE"))

				valueList[i] = en.getValue();
				i++;
				value.append("?,");

			}
		}
		Object[] resvalueList = new Object[i];
		for (int j = 0; j < i; j++) {
			resvalueList[j] = valueList[j];
		}
		key.deleteCharAt(key.lastIndexOf(","));
		value.deleteCharAt(value.lastIndexOf(","));
		StringBuffer sql = new StringBuffer();
		sql.append("insert into  ");
		sql.append(tableName);
		sql.append(" (");
		sql.append(key);
		sql.append(")values(");
		sql.append(value);
		sql.append(")");
		result.put("sql", sql.toString());
		result.put("value", resvalueList);
		return result;
	}

	/**
	 * 将map转成sql语句
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static Map<String, Object> parseMapInsert(String tableName, Map<String, Object> map, List<Map<String, String>> colList) {
		if (tableName == null || tableName.equals("") || map == null || map.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer key = new StringBuffer("");
		StringBuffer value = new StringBuffer("");
		Object[] valueList = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> en : map.entrySet()) {
			if (haveCol(en.getKey(), colList)) {
				if (en.getKey() != null && en.getValue() != null && !en.getKey().toString().equals("")
						&& !en.getValue().toString().equals("")) {
					key.append(en.getKey().toString().replace("$DATE", ""));
					key.append(",");
					// if (en.getKey().toString().contains("$DATE"))

					valueList[i] = en.getValue();
					i++;
					value.append("?,");

				}
			}
		}
		Object[] resvalueList = new Object[i];
		for (int j = 0; j < i; j++) {
			resvalueList[j] = valueList[j];
		}
		key.deleteCharAt(key.lastIndexOf(","));
		value.deleteCharAt(value.lastIndexOf(","));
		StringBuffer sql = new StringBuffer();
		sql.append("insert into  ");
		sql.append(tableName);
		sql.append(" (");
		sql.append(key);
		sql.append(")values(");
		sql.append(value);
		sql.append(")");
		result.put("sql", sql.toString());
		result.put("value", resvalueList);
		return result;
	}

	/**
	 * 将map转成sql更新语句
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static Map<String, Object> parseMapUpdate(String tableName, Map<String, Object> map) {
		if (tableName == null || tableName.equals("") || map == null || map.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer key = new StringBuffer("");
		Object[] valueList = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> en : map.entrySet()) {

			if (en.getKey() != null && en.getValue() != null && !en.getKey().toString().equals("")
					&& !en.getValue().toString().equals("")) {

				if ("INDEX".equals(en.getKey())) {
					key.append("\"");
					key.append(en.getKey().toString().replace("$DATE", ""));
					key.append("\"");
					key.append("=?");
				} else {
					key.append(en.getKey().toString().replace("$DATE", "") + "=?");
				}
				key.append(",");

				valueList[i] = en.getValue();
				i++;

			}
		}
		Object[] resvalueList = new Object[i];
		for (int j = 0; j < i; j++) {
			resvalueList[j] = valueList[j];
		}
		key.deleteCharAt(key.lastIndexOf(","));
		StringBuffer sql = new StringBuffer();
		sql.append("update  ");
		sql.append(tableName);
		sql.append("  set   ");
		sql.append(key);
		sql.append("  where id='" + map.get("ID").toString() + "'");
		result.put("sql", sql.toString());
		result.put("value", resvalueList);
		return result;
	}

	/**
	 * 将map转成sql更新语句(合同模板及合同在线编制使用)
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static Map<String, Object> parseMapUpdateContract(String tableName, Map<String, Object> map) {
		if (tableName == null || tableName.equals("") || map == null || map.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer key = new StringBuffer("");
		Object[] valueList = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> en : map.entrySet()) {

			if (en.getKey() != null && !en.getKey().toString().equals("")) {

				if ("INDEX".equals(en.getKey())) {
					key.append("\"");
					key.append(en.getKey().toString().replace("$DATE", ""));
					key.append("\"");
					key.append("=?");
				} else {
					key.append(en.getKey().toString().replace("$DATE", "") + "=?");
				}
				key.append(",");

				valueList[i] = en.getValue();
				i++;

			}
		}
		Object[] resvalueList = new Object[i];
		for (int j = 0; j < i; j++) {
			resvalueList[j] = valueList[j];
		}
		key.deleteCharAt(key.lastIndexOf(","));
		StringBuffer sql = new StringBuffer();
		sql.append("update  ");
		sql.append(tableName);
		sql.append("  set   ");
		sql.append(key);
		sql.append("  where id='" + map.get("ID").toString() + "'");
		result.put("sql", sql.toString());
		result.put("value", resvalueList);
		return result;
	}

	/**
	 * 将map转成sql更新语句
	 * 
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static Map<String, Object> parseMapUpdate(String tableName, Map<String, Object> map, List<Map<String, String>> colList) {
		if (tableName == null || tableName.equals("") || map == null || map.size() == 0) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer key = new StringBuffer("");
		Object[] valueList = new Object[map.size()];
		int i = 0;
		for (Entry<String, Object> en : map.entrySet()) {
			if (haveCol(en.getKey(), colList)) {
				if (en.getKey() != null && !en.getKey().toString().equals("")) {
					key.append(en.getKey().toString().replace("$DATE", "") + "=?");
					key.append(",");

					valueList[i] = en.getValue();
					i++;

				}
			}
		}
		Object[] resvalueList = new Object[i];
		for (int j = 0; j < i; j++) {
			resvalueList[j] = valueList[j];
		}
		key.deleteCharAt(key.lastIndexOf(","));
		StringBuffer sql = new StringBuffer();
		sql.append("update  ");
		sql.append(tableName);
		sql.append("  set   ");
		sql.append(key);
		sql.append("  where id='" + map.get("ID").toString() + "'");
		result.put("sql", sql.toString());
		result.put("value", resvalueList);
		return result;
	}

	/**
	 * 将一逗号间隔的参数，转成加引号sql识别的参数
	 * 
	 * @param str
	 * @return
	 */
	public static String parseSqlArgs(String str) {
		if (str == null || str.equals("")) {
			return "''";
		}
		str = "'" + str + "'";
		str = str.replace(",", "','");
		return str;
	}

	public static boolean haveCol(String key, List<Map<String, String>> list) {
		for (Map map : list) {
			if (map.containsValue(key)) {
				return true;
			}
		}
		return false;
	}

	public static String parseStr(Object objIn) {
		if (objIn == null || objIn.equals("")) {
			return "";
		}
		return objIn.toString();
	}

	public static boolean isEmpty(Object objIn) {
		if (objIn == null || objIn.equals("")) {
			return true;
		}
		return false;
	}

	public static java.util.Date parseDate(Object objIn) {
		return parseDate(objIn, new java.util.Date());
	}

	public static java.util.Date parseDate(Object objIn, java.util.Date defaultValue) {
		java.util.Date datRe = defaultValue;
		if (objIn == null)
			return datRe;

		try {
			if (objIn instanceof java.util.Date) {
				return (java.util.Date) objIn;
			}
			if (objIn instanceof java.sql.Date) {
				return new java.util.Date(((java.sql.Date) objIn).getTime());
			}
			if (objIn instanceof Timestamp) {
				return new java.util.Date(((Timestamp) objIn).getTime());
			}

			datRe = new java.util.Date();
		} catch (Exception localException) {
		}

		return datRe;
	}

	/**
	 * 根据class对象获取当前的HQL语句 LIST(无参数)
	 *
	 * @param clazz
	 * @return
	 *
	 */
	public static String getListHql(Class clazz) {
		StringBuilder builder = new StringBuilder(128);
		builder.append(" from ");
		builder.append(clazz.getSimpleName());
		return builder.toString();
	}

	/**
	 * 根据class对象获取当前的HQL语句 LIST(无参数)
	 *
	 * @param order
	 * @param clazz
	 * @return
	 *
	 */
	public static String getListHql(String order, Class clazz) {
		StringBuilder builder = new StringBuilder(128);
		builder.append(" from ");
		builder.append(clazz.getSimpleName());
		if (!StringUtils.isEmpty(order)) {
			builder.append(" order by ");
			builder.append(order);
		}
		return builder.toString();
	}

	/**
	 * 根据class对象获取当前的HQL语句 LIST(带参数)
	 *
	 * @param clazz
	 * @param property
	 * @return
	 *
	 */
	public static String getListHql(Class clazz, String... property) {
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
		if (property != null && property.length > 0) {
			builder.append(" where ");
			for (int i = 0; i < property.length; i++) {
				builder.append(property[i]);

				if (i == property.length - 1)
					builder.append(" = ?");
				else
					builder.append(" = ? and ");
			}
		}
		return builder.toString();
	}

	/**
	 * 根据class对象获取当前的HQL语句 LIST(带参数)
	 *
	 * @param order
	 * @param clazz
	 * @param property
	 * @return
	 *
	 */
	public static String getListHql(String order, Class clazz, String... property) {
		StringBuilder builder = new StringBuilder(256);
		builder.append(getListHql(clazz));
		if (property != null && property.length > 0) {
			builder.append(" where ");
			for (int i = 0; i < property.length; i++) {
				builder.append(property[i]);

				if (i == property.length - 1)
					builder.append(" = ?");
				else
					builder.append(" = ? and ");
			}
		}
		if (!StringUtils.isEmpty(order)) {
			builder.append(" order by ");
			builder.append(order);
		}
		return builder.toString();
	}

	/**
	 * 根据class对象获取当前的HQL语句 COUNT(无参数)
	 *
	 * @param clazz
	 * @return
	 *
	 */
	public static String getCountHql(Class clazz) {
		StringBuilder builder = new StringBuilder(128);
		builder.append("select count(*) from ");
		builder.append(clazz.getSimpleName());
		return builder.toString();
	}

	/**
	 * 根据class对象获取当前的HQL语句 COUNT(有参数)
	 *
	 * @param clazz
	 * @param property
	 * @return
	 *
	 */
	public static String getCountHql(Class clazz, String... property) {
		StringBuilder builder = new StringBuilder(256);
		builder.append(getCountHql(clazz));
		if (property != null && property.length > 0) {
			builder.append(" where ");
			for (int i = 0; i < property.length; i++) {
				builder.append(property[i]);

				if (i == property.length - 1)
					builder.append(" = ?");
				else
					builder.append(" = ? and ");
			}

		}
		return builder.toString();
	}

}
