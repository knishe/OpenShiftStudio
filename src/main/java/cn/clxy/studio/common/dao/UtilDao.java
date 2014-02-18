package cn.clxy.studio.common.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import cn.clxy.studio.common.data.IDData;

/**
 * 工具Dao类。提供通常的insert，update，delete by id和select all 等功能。
 * @author clxy
 * @param <T>
 */
public interface UtilDao {

	/**
	 * Insert。注意，Null值也会被insert。
	 * @param object
	 * @return
	 */
	@InsertProvider(type = SqlProvider.class, method = "insert")
	public int insert(Object object);

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值也会被更新。
	 * @param object
	 * @return
	 */
	@UpdateProvider(type = SqlProvider.class, method = "update")
	public int update(IDData object);

	/**
	 * Update。条件是id和update时间（如果存在的话）。注意，Null值不会被更新。
	 * @param object
	 * @return
	 */
	@UpdateProvider(type = SqlProvider.class, method = "updateNotNull")
	public int updateNotNull(IDData object);

	/**
	 * Delete。条件是id和update时间（如果存在的话）。
	 * @param object
	 * @return
	 */
	public int deleteById(IDData object);

	/**
	 * 无条件选择所有。
	 * @param clazz
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "findAll")
	public <T> List<T> findAll(Class<T> clazz);
}
