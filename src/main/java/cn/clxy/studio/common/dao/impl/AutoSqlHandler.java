package cn.clxy.studio.common.dao.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.springframework.util.StringUtils;

import cn.clxy.studio.common.dao.MybatisDispatcher.Handler;
import cn.clxy.studio.common.dao.UtilDao;

/**
 * Not finished!
 * @author clxy
 * @deprecated 改用UtilDao方式。
 * @see UtilDao
 */
public class AutoSqlHandler implements Handler {

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		Object[] args = invocation.getArgs();
		MappedStatement statement = MybatisUtil.getStatement(invocation);
		String id = statement.getId();
		if (!methods.contains("auto." + id)) {
			// 如果该方法不处理则stop。
			return null;
		}

		Object argParam = args[1];
		BoundSql originalBound = statement.getBoundSql(argParam);
		if (!StringUtils.isEmpty(originalBound.getSql().trim())) {
			// 如果子类用指定SQL覆盖该方法则Skip。
			return null;
		}

		Class<?> clazz = argParam.getClass();
		Map<String, String> sqls = null;
		synchronized (caches) {
			if (caches.containsKey(clazz)) {
				sqls = caches.get(clazz);
			} else {
				sqls = new HashMap<String, String>();
				caches.put(clazz, sqls);
			}
		}
		String sql = sqls.get(id);
		return null;
	}

	private static final Set<String> methods = new HashSet<String>() {
		private static final long serialVersionUID = 1L;

		{
			addAll(Arrays.asList("insert", "update", "updateNotNull"));
		}
	};
	private static final Map<Class<?>, Map<String, String>> caches = new HashMap<>();
}