package cn.clxy.studio.common.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.plugin.Invocation;

import cn.clxy.studio.common.dao.MybatisDispatcher.Handler;

public class Handlers implements Handler {

	private List<Handler> handlers = new ArrayList<>();

	@Override
	public Object handle(Invocation invocation) throws Throwable {

		for (Handler handler : handlers) {
			Object result = handler.handle(invocation);
			if (result != null) {
				return result;
			}
		}

		return invocation.proceed();
	}

	public void setHandlers(List<Handler> handlers) {
		this.handlers = handlers;
	}
}
