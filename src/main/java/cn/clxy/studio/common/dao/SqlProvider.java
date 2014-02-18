package cn.clxy.studio.common.dao;

import java.beans.PropertyDescriptor;
import java.beans.Transient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

import cn.clxy.studio.common.AppConfig;
import cn.clxy.studio.common.data.IDData;
import cn.clxy.studio.common.util.AnnotationUtil;
import cn.clxy.studio.common.util.BeanUtil;

@SuppressWarnings("rawtypes")
public class SqlProvider {

	public String insert(Object object) {
		return getSqlInfo(object).insert;
	}

	public String update(Object object) {
		return getSqlInfo(object).update;
	}

	public String updateNotNull(Object object) {
		SqlInfo si = getSqlInfo(object);
		String sql = "update " + si.tableName + " set ";

		List<String> updates = new ArrayList<>();
		for (Entry<String, String> e : si.updateMap.entrySet()) {
			if (BeanUtil.getProperty(object, e.getKey()) != null) {
				updates.add(e.getValue());
			}
		}

		sql += StringUtils.collectionToCommaDelimitedString(updates);
		sql = sql + idCondition + getUpdateAt(object);

		return sql;
	}

	public String deleteById(Object object) {
		return getSqlInfo(object).deleteById;
	}

	public String findAll(Object object) {
		return getSqlInfo(object).findAll;
	}

	/**
	 * 取得SQL信息。<br>
	 * 无需synchronized，因为不会造成错误。
	 * @param clazz
	 * @return
	 */
	private SqlInfo getSqlInfo(Object object) {

		Class<?> clazz = object.getClass();
		SqlInfo result = caches.get(clazz);
		if (result == null) {
			result = createSqlInfo(clazz);
			caches.put(clazz, result);
		}
		return result;
	}

	/**
	 * SQL文数据类。
	 * @author clxy
	 */
	final static class SqlInfo {
		public String tableName;
		public String findAll;
		public String deleteById;
		public String insert;
		public String update;
		public Map<String, String> updateMap;
	}

	/**
	 * 创建SQL文。
	 * @param clazz
	 * @return
	 */
	private SqlInfo createSqlInfo(Class<?> clazz) {

		SqlInfo si = new SqlInfo();
		String className = clazz.getSimpleName();
		String tableName = toDBString(className);

		si.tableName = tableName;
		si.findAll = "select * from " + tableName;
		si.deleteById = "delete from " + tableName + idCondition;

		boolean isIdData = clazz.isAssignableFrom(IDData.class);
		PropertyDescriptor[] props = PropertyUtils.getPropertyDescriptors(clazz);
		List<String> cols = new ArrayList<>();
		List<String> vals = new ArrayList<>();
		Map<String, String> updateMap = new HashMap<>();

		for (PropertyDescriptor prop : props) {

			if (prop.getReadMethod() == null) {
				continue;
			}

			String name = prop.getName();
			if ("class".equals(name) || ("id".equals(name) && isIdData) || isIgnore(prop)) {
				continue;
			}
			String col = toDBString(name);
			String val = toValueString(name);
			cols.add(col);
			vals.add(val);
			updateMap.put(name, col + " = " + val);
		}

		si.insert = "insert into " + tableName + " ("
				+ StringUtils.collectionToCommaDelimitedString(cols) + ") " + "values ("
				+ StringUtils.collectionToCommaDelimitedString(vals) + ")";

		si.update = "update " + tableName + " set "
				+ StringUtils.collectionToCommaDelimitedString(updateMap.values()) + idCondition;
		si.updateMap = updateMap;

		return si;
	}

	/**
	 * 追加更新时间。
	 * @param obj
	 * @return
	 */
	private String getUpdateAt(Object obj) {
		if (BeanUtil.getProperty(obj, "updateAt") == null) {
			return "";
		}
		return " and update_at = #{updateAt}";
	}

	/**
	 * 追加下划线。注意，大小写没有整理。
	 * @param str
	 * @return
	 */
	private String toDBString(String str) {
		return hasUnderScore ? str.replaceAll("([a-z])([A-Z])", "$1_$2") : str;
	}

	/**
	 * 追加MyBatis格式。
	 * @param str
	 * @return
	 */
	private String toValueString(String str) {
		return "#{" + str + "}";
	}

	@SuppressWarnings("unchecked")
	private boolean isIgnore(PropertyDescriptor pd) {
		for (Class clazz : ignoreAnnotations) {
			if (AnnotationUtil.find(pd, clazz) != null) {
				return true;
			}
		}
		return false;
	}

	private static final Set<Class> ignoreAnnotations = new HashSet<Class>() {
		private static final long serialVersionUID = 1L;

		{
			add(Transient.class);
		}
	};

	private static final String idCondition = " where id=#{id}";
	private static final boolean hasUnderScore = AppConfig.getBoolean("mapUnderscoreToCamelCase");
	private static final Map<Class<?>, SqlInfo> caches = new HashMap<>();
}
