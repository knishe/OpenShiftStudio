package cn.clxy.studio.common.aop;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.clxy.studio.common.action.NoAuth;
import cn.clxy.studio.common.data.User;
import cn.clxy.studio.common.util.AnnotationUtil;
import cn.clxy.studio.common.web.WebUtil;

/**
 * ログインか否かをチェックする。<br>
 * 简单实现。TODO 复杂的时候也许用Spring Security？好重的样子。
 * @author clxy
 */
public class AuthCheckInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		User user = WebUtil.getUser();
		if (user != null && user.isLogined()) {
			// ログイン済み＝OK。
			return true;
		}

		HandlerMethod method = (HandlerMethod) handler;
		if (getAnnotation(method) != null) {
			// ログイン必要ない場合OK。
			return true;
		}

		boolean isAjax = WebUtil.isAjax(request);
		if (isAjax) {// Ajax
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		} else {
			response.sendRedirect(request.getContextPath() + "login");// TODO login exists?
		}

		return false;
	}

	/**
	 * 没必要对应多线程！<br>
	 * 以后可能需要替换，所以没有放进ActionUtil。
	 * @param method
	 * @return
	 */
	private NoAuth getAnnotation(HandlerMethod method) {

		if (authMapping.containsKey(method)) {
			return authMapping.get(method);
		}

		NoAuth result = AnnotationUtil.find(method, NoAuth.class);
		authMapping.put(method, result);
		return result;
	}

	private static final Map<HandlerMethod, NoAuth> authMapping = new HashMap<>();
}
