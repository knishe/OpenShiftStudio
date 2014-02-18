package cn.clxy.studio.home.dao;

import org.apache.ibatis.annotations.Select;

import cn.clxy.studio.home.data.Reputation;

public interface ReputationDao {

	@Select("select * from Reputation where site=#{value} order by update_at desc limit 1")
	Reputation findBySite(String site);
}
