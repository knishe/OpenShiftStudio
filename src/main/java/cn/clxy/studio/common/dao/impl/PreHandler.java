package cn.clxy.studio.common.dao.impl;

import java.util.Date;

import org.apache.ibatis.plugin.Invocation;

import cn.clxy.studio.common.dao.MybatisDispatcher.Handler;
import cn.clxy.studio.common.data.BaseData;
import cn.clxy.studio.common.data.User;
import cn.clxy.studio.common.web.WebUtil;

public class PreHandler implements Handler {

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		Object argParam = MybatisUtil.getParameter(invocation);
		if (!(argParam instanceof BaseData) || MybatisUtil.isNotNullMethod(invocation)) {
			return null;
		}

		BaseData data = (BaseData) argParam;
		Date now = new Date();
		if (data.getCreateAt() == null) {
			data.setCreateAt(now);
		}
		if (data.getUpdateAt() == null) {
			data.setUpdateAt(now);// TODO 做版本控制时该如何？
		}

		User user = WebUtil.getUser();
		if (user == null) {
			return null;
		}
		if (data.getCreateBy() == null) {
			data.setCreateBy(user.getUserId());
		}
		if (data.getUpdateBy() == null) {
			data.setUpdateBy(user.getUserId());
		}

		return null;
	}
}