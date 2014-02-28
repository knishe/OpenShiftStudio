package cn.clxy.studio.common.web;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cn.clxy.studio.common.data.User;

/**
 * セッション生存期間を管理する。
 * @author clxy
 */
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {

		// WebUtil#setUser利用できないため。
		event.getSession().setAttribute(WebUtil.KEY_USER, new User());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// ユーザーの一時的なものがあれば、クリアする。
	}
}
