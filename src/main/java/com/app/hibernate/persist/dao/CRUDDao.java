package com.app.hibernate.persist.dao;

import java.util.List;
import java.util.Map;

public interface CRUDDao {

	/**
	 *
	 * 根据hql获取数量
	 * 
	 * @param hql
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long queryCountWithHql(String hql);

	/**
	 *
	 * 根据hql获取数量
	 * 
	 * @param hql
	 * @param params
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long queryCountWithHql(String hql, Map<String, Object> params);

	/**
	 *
	 * 执行insert、update hql语句
	 * 
	 * @param hql
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public int executeHql(String hql);

	/**
	 *
	 * 执行insert、update hql语句
	 * 
	 * @param hql
	 * @param params
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public int executeHql(String hql, Map<String, Object> params);

	/**
	 *
	 * 执行insert、update sql语句
	 * 
	 * @param sql
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public int executeSql(String sql);

	/**
	 *
	 * 执行insert、update sql语句
	 * 
	 * @param sql
	 * @param params
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public int executeSql(String sql, Map<String, Object> params);

	/**
	 *
	 * 根据sql获取数量
	 * 
	 * @param sql
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long queryCountWithSql(String sql);

	/**
	 *
	 * 根据sql获取数量
	 * 
	 * @param sql
	 * @param params
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public long queryCountWithSql(String sql, Map<String, Object> params);

	/**
	 * 执行sql语句返回list<map>结构
	 *
	 * @param sql
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:26
	 * @version 1.0
	 */
	public List queryListWithSql(String sql);

	/**
	 * 执行sql语句返回map结构
	 *
	 * @param sql
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:53
	 * @version 1.0
	 */
	public Map queryMapWithSql(String sql);

	/**
	 * 执行sql语句返回list<map>结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:56
	 * @version 1.0
	 */
	public List queryListWithSql(String sql, Map<String, Object> args);

	/**
	 * 执行sql语句返回list<map>结构 带参数(分页)
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:56
	 * @version 1.0
	 */
	public List queryListWithSql(String sql, Map<String, Object> args, int page, int rows);

	/**
	 * 执行sql语句返回map结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午10:00:00
	 * @version 1.0
	 */
	public Map queryMapWithSql(String sql, Map<String, Object> args);

	/**
	 * 执行修改操作
	 *
	 * @param sql
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 下午5:10:26
	 * @version 1.0
	 */
	public int executeSqlUpdate(String sql);

	/**
	 * 执行修改操作 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 下午5:10:41
	 * @version 1.0
	 */
	public int executeSqlUpdate(String sql, Map<String, Object> args);

	/**
	 * 执行sql语句返回list<map>结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:56
	 * @version 1.0
	 */
	public List queryListWithSql(String sql, Object[] args);

	/**
	 * 执行sql语句返回map结构 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午10:00:00
	 * @version 1.0
	 */
	public Map queryMapWithSql(String sql, Object[] args);

	/**
	 * 执行修改操作 带参数
	 *
	 * @param sql
	 * @param args
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 下午5:10:41
	 * @version 1.0
	 */
	public int executeSqlUpdate(String sql, Object[] args);

	/**
	 * 
	 * 根据class生成Hql 执行
	 * 
	 * @param clazz
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 * 
	 */
	public List queryListWithHql(Class clazz);

	/**
	 *
	 * 根据class生成Hql 执行
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
	public List queryListWithHql(Class clazz, String property, Object value);

	/**
	 *
	 * 根据class生成Hql 执行 (可以强转为需要的对象)
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
	public Object queryMapWithHql(Class clazz, String property, Object value);

	/**
	 *
	 * 根据Hql语句查询
	 * 
	 * @param hql
	 * @author Caratacus
	 * @date 2016/4/29 0029
	 * @return
	 * @throws
	 * @version 1.0
	 *
	 */
	public List<Map> queryListWithHql(String hql);

	/**
	 *
	 * 根据class生成Hql 执行
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
	public List queryListWithHql(Class clazz, String[] property, Object... value);

	/**
	 *
	 * 根据class生成Hql 执行
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
	public List queryListWithHql(Class clazz, Map<String, Object> map);

	/**
	 * 执行sql语句返回list<map>结构 (分页)
	 *
	 * @param sql
	 * @return
	 * @author Caratacus
	 * @date 2015年12月1日 上午9:59:26
	 * @version 1.0
	 */
	public List queryListWithSql(String sql, int page, int rows);
}
