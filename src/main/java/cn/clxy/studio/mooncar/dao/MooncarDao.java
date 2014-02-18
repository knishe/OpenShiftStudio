package cn.clxy.studio.mooncar.dao;

import java.util.Collection;
import java.util.List;

import cn.clxy.studio.mooncar.data.NameData;
import cn.clxy.studio.mooncar.data.OriginNameData;

/**
 * @author clxy
 */
public interface MooncarDao {

	public Collection<OriginNameData> findBySite(String site);

	public Collection<NameData> findByName(String name);

	public void save(List<?> datas);

}
