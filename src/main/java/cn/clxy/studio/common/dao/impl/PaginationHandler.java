package cn.clxy.studio.common.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;

import cn.clxy.studio.common.dao.Dialect;
import cn.clxy.studio.common.dao.MybatisDispatcher.Handler;
import cn.clxy.studio.common.data.PaginationData;

/**
 * 自动分页拦截器。
 * <ul>
 * <li>是否分页取决于查询参数是否是PaginationData类。</li>
 * <li>先查询总件数，如果总件数为0，不再继续查询下去。</li>
 * <li>完全无视MyBatis的RowBounds。不可并用！</li>
 * </ul>
 * 参考自https://github.com/yfyang/mybatis-pagination
 * @author clxy
 */
public class PaginationHandler implements Handler {

	private Dialect dialect;

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		Object argParam = MybatisUtil.getParameter(invocation);
		if (!(argParam instanceof PaginationData)) {
			// 如果参数不是分页条件，不处理。
			return null;
		}

		MappedStatement statement = MybatisUtil.getStatement(invocation);
		BoundSql originalBound = statement.getBoundSql(argParam);
		String originalSql = originalBound.getSql().trim();

		// 件数：select count。
		String countSql = "select count(1) from (" + originalSql + ") as auto_count";
		log.debug(countSql);
		int count = getCount(statement, originalBound, countSql);
		if (count == 0) {
			// 结果为空时不再继续查询。
			return Collections.EMPTY_LIST;
		}
		PaginationData condition = (PaginationData) argParam;
		condition.setCount(count);

		// 分页：set limit and offset。
		String pageSql =
				dialect.getLimitString(originalSql, condition.getOffset(), condition.getLimit());
		log.debug(pageSql);
		invocation.getArgs()[0] = MybatisUtil.createBy(statement, originalBound, pageSql);

		return invocation.proceed();
	}

	/**
	 * 查询总纪录数。
	 * @param statement
	 * @param originalBound
	 * @param countSql
	 * @return
	 * @throws SQLException
	 */
	private static int getCount(MappedStatement statement, BoundSql originalBound, String countSql)
			throws SQLException {

		Object param = originalBound.getParameterObject();
		BoundSql countBound = new BoundSql(
				statement.getConfiguration(), countSql, originalBound.getParameterMappings(), param);

		Connection conn =
				statement.getConfiguration().getEnvironment().getDataSource().getConnection();

		try (PreparedStatement ps = conn.prepareStatement(countSql)) {

			MybatisUtil.setParameters(ps, statement, countBound, param);
			ResultSet rs = ps.executeQuery();
			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
			}
			rs.close();
			return count;
		}
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	private static final Log log = LogFactory.getLog(PaginationHandler.class);
}
