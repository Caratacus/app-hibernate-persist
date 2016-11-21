/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.app.hibernate.persist.query;

import com.app.hibernate.persist.utils.CollectionUtils;
import com.app.hibernate.persist.utils.StringUtils;

import java.text.MessageFormat;
import java.util.Collection;

/**
 * <p>
 * 实现AbstractSQL ，实现WHERE条件自定义
 * </p>
 *
 * @author yanghu , Caratacus
 * @Date 2016-08-22
 */
@SuppressWarnings("serial")
public class SqlPlus extends AbstractSQL<SqlPlus> {

	private final String IS_NOT_NULL = " IS NOT NULL";
	private final String IS_NULL = " IS NULL";
	private final String SQL_LIKE = " LIKE CONCAT(CONCAT({0},{1}),{2})";
	private final String SQL_BETWEEN_AND = " BETWEEN {0} AND {1}";

	@Override
	public SqlPlus getSelf() {
		return this;
	}

	/**
	 * 将LIKE语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            like值,无需前后%, MYSQL及ORACEL通用
	 * @return
	 */
	public SqlPlus LIKE(String column, String value) {
		handerLike(column, value, false);
		return this;
	}

	/**
	 * 将LIKE语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            like值,无需前后%, MYSQL及ORACEL通用
	 * @return
	 */
	public SqlPlus NOT_LIKE(String column, String value) {
		handerLike(column, value, true);
		return this;
	}

	/**
	 * IS NOT NULL查询
	 *
	 * @param columns
	 *            以逗号分隔的字段名称
	 * @return this
	 */
	public SqlPlus IS_NOT_NULL(String columns) {
		handerNull(columns, IS_NOT_NULL);
		return this;
	}

	/**
	 * IS NULL查询
	 *
	 * @param columns
	 *            以逗号分隔的字段名称
	 * @return
	 */
	public SqlPlus IS_NULL(String columns) {
		handerNull(columns, IS_NULL);
		return this;
	}

	/**
	 * 处理LIKE操作
	 *
	 * @param column
	 *            字段名称
	 * @param value
	 *            like匹配值
	 * @param isNot
	 *            是否为NOT LIKE操作
	 */
	private void handerLike(String column, String value, boolean isNot) {
		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {
			StringBuilder inSql = new StringBuilder();
			inSql.append(column);
			if (isNot) {
				inSql.append(" NOT");
			}
			inSql.append(MessageFormat.format(SQL_LIKE, "'%'", StringUtils.quotaMark(value), "'%'"));
			WHERE(inSql.toString());
		}
	}

	/**
	 * 将IN语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            List集合
	 * @return
	 */
	public SqlPlus IN(String column, Collection<?> value) {
		handerIn(column, value, false);
		return this;
	}

	/**
	 * 将IN语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            List集合
	 * @return
	 */
	public SqlPlus NOT_IN(String column, Collection<?> value) {
		handerIn(column, value, true);
		return this;
	}

	/**
	 * 将IN语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            逗号拼接的字符串
	 * @return
	 */
	public SqlPlus IN(String column, String value) {
		handerIn(column, value, false);
		return this;
	}

	/**
	 * 将IN语句添加到WHERE条件中
	 *
	 * @param column
	 *            字段名
	 * @param value
	 *            逗号拼接的字符串
	 * @return
	 */
	public SqlPlus NOT_IN(String column, String value) {
		handerIn(column, value, true);
		return this;
	}

	/**
	 * 将EXISTS语句添加到WHERE条件中
	 *
	 * @param value
	 * @return
	 */
	public SqlPlus EXISTS(String value) {
		handerExists(value, false);
		return this;
	}

	/**
	 * 处理EXISTS操作
	 *
	 * @param value
	 * @param isNot
	 *            是否为NOT EXISTS操作
	 */
	private void handerExists(String value, boolean isNot) {
		if (StringUtils.isNotBlank(value)) {
			StringBuilder inSql = new StringBuilder();
			if (isNot) {
				inSql.append(" NOT");
			}
			inSql.append(" EXISTS (").append(value).append(")");
			WHERE(inSql.toString());
		}
	}

	/**
	 * 将NOT_EXISTS语句添加到WHERE条件中
	 *
	 * @param value
	 * @return
	 */
	public SqlPlus NOT_EXISTS(String value) {
		handerExists(value, true);
		return this;
	}

	/**
	 * 处理IN操作
	 *
	 * @param column
	 *            字段名称
	 * @param value
	 *            集合List
	 * @param isNot
	 *            是否为NOT IN操作
	 */
	private void handerIn(String column, Collection<?> value, boolean isNot) {
		if (StringUtils.isNotBlank(column) && CollectionUtils.isNotEmpty(value)) {
			StringBuilder inSql = new StringBuilder();
			inSql.append(column);
			if (isNot) {
				inSql.append(" NOT");
			}
			inSql.append(" IN ");
			inSql.append(StringUtils.quotaMarkList(value));
			WHERE(inSql.toString());
		}
	}

	/**
	 * 处理IN操作
	 *
	 * @param column
	 *            字段名称
	 * @param value
	 *            逗号拼接的字符串
	 * @param isNot
	 *            是否为NOT IN操作
	 */
	private void handerIn(String column, String value, boolean isNot) {
		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(value)) {
			StringBuilder inSql = new StringBuilder();
			inSql.append(column);
			if (isNot) {
				inSql.append(" NOT");
			}
			inSql.append(" IN (").append(value).append(")");
			WHERE(inSql.toString());
		}
	}

	/**
	 * 处理BETWEEN_AND操作
	 *
	 * @param column
	 *            字段名称
	 * @param val1
	 * @param val2
	 */
	public SqlPlus BETWEEN_AND(String column, String val1, String val2) {
		between(column, val1, val2);
		return this;
	}

	/**
	 * 处理BETWEEN_AND操作
	 *
	 * @param column
	 *            字段名称
	 * @param val1
	 * @param val2
	 */
	private void between(String column, String val1, String val2) {
		if (StringUtils.isNotBlank(column) && StringUtils.isNotBlank(val1) && StringUtils.isNotBlank(val2)) {
			StringBuilder betweenSql = new StringBuilder();
			betweenSql.append(column);
			betweenSql.append(MessageFormat.format(SQL_BETWEEN_AND, StringUtils.quotaMark(val1), StringUtils.quotaMark(val2)));
			WHERE(betweenSql.toString());
		}
	}

	/**
	 * 以相同的方式处理null和notnull
	 *
	 * @param columns
	 *            以逗号分隔的字段名称
	 * @param sqlPart
	 *            SQL部分
	 */
	private void handerNull(String columns, String sqlPart) {
		if (StringUtils.isNotBlank(columns)) {
			String[] cols = columns.split(",");
			for (String col : cols) {
				if (StringUtils.isNotBlank(col.trim())) {
					WHERE(col + sqlPart);
				}
			}
		}
	}
}
