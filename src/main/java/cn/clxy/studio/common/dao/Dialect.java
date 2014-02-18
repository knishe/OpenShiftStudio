package cn.clxy.studio.common.dao;

public interface Dialect {
	String getLimitString(String sql, int offset, int limit);
}
