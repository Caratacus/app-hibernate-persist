package com.app.hibernate.persist.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.app.hibernate.persist.utils.SqlUtil;

public interface HibernateDao<T> {
	/**
	 * 保存方法
	 *
	 * @param o
	 * @return
	 */
	public T save(T o);

	/**
	 * 删除方法
	 *
	 * @param o
	 */
	public void delete(T o);

	/**
	 * 修改方法
	 *
	 * @param o
	 */
	public void update(T o);

	/**
	 * 保存/修改方法
	 *
	 * @param o
	 */
	public void saveOrUpdate(T o);

	/**
	 * 根据id获取对象
	 *
	 * @param c
	 * @param id
	 * @return
	 */
	public T get(Class<T> c, Serializable id);

	/**
	 * 根据hql获取对象
	 *
	 * @param hql
	 * @return
	 */
	public T get(String hql);

	/**
	 * 根据hql获取对象
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public T get(String hql, Map<String, Object> params);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @return
	 */
	public List<T> query(String hql);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param params
	 * @return
	 */
	public List<T> query(String hql, Map<String, Object> params);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> query(String hql, int page, int rows);

	/**
	 * 查询结果集
	 *
	 * @param hql
	 * @param params
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<T> query(String hql, Map<String, Object> params, int page, int rows);

	/**
	 * 批量添加
	 *
	 * @param list
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/24 0024
	 * @version 1.0
	 */
	public void insertWithBatch(List<T> list);

	/**
	 * 批量修改
	 *
	 * @param list
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/24 0024
	 * @version 1.0
	 */
	public void updateWithBatch(List<T> list);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String property, Object value);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String[] property, Object... value);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param map
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, Map<String, Object> map);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, String property, Object value);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, String[] property, Object... value);

	/**
	 * 查询结果集
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map);

	/**
	 *
	 * 根据class生成count语句执行
	 *
	 * @param clazz
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long count(Class clazz);

	/**
	 *
	 * 根据class生成count语句执行
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long count(Class clazz, String property, Object... value);

	/**
	 *
	 * 根据class生成count语句执行
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long count(Class clazz, String[] property, Object... value);

	/**
	 *
	 * 根据class生成count语句执行
	 *
	 * @param clazz
	 * @param map
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long count(Class clazz, Map<String, Object> map);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param order
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String order);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String order, int page, int rows);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String order, String property, Object value);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, String order, String[] property, Object... value);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param map
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, Map<String, Object> map, String order);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, String order, String property, Object value);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param property
	 * @param value
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, String order, String[] property, Object... value);

	/**
	 * 查询结果集排序
	 *
	 * @param clazz
	 * @param page
	 * @param rows
	 * @param map
	 * @return
	 * @throws
	 * @author Caratacus
	 * @date 2016/5/25 0025
	 * @version 1.0
	 */
	public List<T> query(Class<T> clazz, int page, int rows, Map<String, Object> map, String order);

}
