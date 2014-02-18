package cn.clxy.studio.common.dao.impl;

import cn.clxy.studio.common.dao.Dialect;

public class LimitOffsetDialect implements Dialect {

	@Override
	public String getLimitString(String sql, int offset, int limit) {
		return sql + " limit " + offset + ", " + limit;
	}
}
