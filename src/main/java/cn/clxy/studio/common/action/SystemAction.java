package cn.clxy.studio.common.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * システム例外。
 */
@NoAuth
@Layout(none = true)
public class SystemAction {

	@RequestMapping(value = "systemException*")
	public Object err500(HttpServletRequest request) {
		return "systemError";
	}

	@RequestMapping(value = "notFound*")
	public Object err404(HttpServletRequest request) {
		return "404";
	}
}
