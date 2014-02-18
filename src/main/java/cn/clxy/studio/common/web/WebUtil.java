package cn.clxy.studio.common.web;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;
import static org.springframework.web.context.request.RequestAttributes.SCOPE_SESSION;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.FORWARD_URL_PREFIX;
import static org.springframework.web.servlet.view.UrlBasedViewResolver.REDIRECT_URL_PREFIX;
import static org.springframework.web.util.WebUtils.ERROR_EXCEPTION_ATTRIBUTE;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import cn.clxy.studio.common.AppConfig;
import cn.clxy.studio.common.data.User;
import cn.clxy.studio.common.exception.BaseException;
import cn.clxy.studio.common.exception.SystemException;

/**
 * Web関連ツール。
 * @author clxy
 */
public class WebUtil {

	/**
	 * セッションにユーザーのキー。
	 */
	public static final String KEY_USER = "user";

	static ServletContext servletContext;

	public static InputStream load(String url) {

		if (url.toLowerCase().startsWith("http")) {
			return loadOnline(url);
		}
		return loadLocal(url);
	}

	private static InputStream loadLocal(String url) {
		return servletContext.getResourceAsStream(url);
	}

	/**
	 * 使用jsoup.response.bodyAsBytes得到byte[]，用ByteArrayInputStream包装，unzip时抛EOFException。<br>
	 * 因此使用HttpURLConnection。
	 * @param url
	 * @return
	 */
	private static InputStream loadOnline(String url) {

		for (int i = 0;; i++) {

			try {
				HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
				huc.setConnectTimeout(timeout);
				huc.connect();

				return huc.getInputStream();

			} catch (Exception e) {

				if (i > retry) {
					throw new RuntimeException(e);
				}

				log.warn("Timeout (" + i + "): " + url);
				try {
					Thread.sleep(timeout / 2);
				} catch (Exception te) {
					// ignore!
				}
				continue;
			}
		}
	}

	/**
	 * カレントのLocale取得する。
	 * @return
	 */
	public static Locale getLocale() {
		return RequestContextUtils.getLocale(getRequestAttributes().getRequest());
	}

	/**
	 * 保存异常。
	 * @param exception
	 * @param isRedirect
	 */
	public static void saveError(Object exception, boolean isRedirect) {
		int scope = isRedirect ? SCOPE_SESSION : SCOPE_REQUEST;
		getRequestAttributes().setAttribute(ERROR_EXCEPTION_ATTRIBUTE, exception, scope);
	}

	/**
	 * エラーなどメッセージを取得する。
	 * @return
	 */
	public static List<ObjectError> getErrors() {

		Exception exception = (Exception) getAttribute(ERROR_EXCEPTION_ATTRIBUTE);
		if (exception == null) {
			return null;
		}

		if (exception instanceof BindException) {
			return ((BindException) exception).getAllErrors();
		}

		if (!(exception instanceof BaseException)) {
			exception = new SystemException(exception);
		}

		ObjectError error = ((BaseException) exception).getError();
		return Arrays.asList(new ObjectError[] { error });
	}

	/**
	 * セッションを無効化する。
	 * @param session
	 */
	public static void invalidate() {
		HttpSession session = getSession();
		if (session != null) {
			session.invalidate();
		}
	}

	/**
	 * Jsp名前でUrlを取得する。
	 * @param view
	 * @return
	 */
	public static String getJspUrl(String view) {

		if (StringUtils.isEmpty(view)) {
			return view;
		}

		String slash = AppConfig.SLASH;
		if (view.startsWith(slash)) {
			view = view.substring(slash.length());
		}

		String prefix = AppConfig.get("jspPrefix");
		String suffix = AppConfig.get("jspSuffix");
		return prefix + view + suffix;
	}

	/**
	 * Ajaxの方式にもかかわらず、JSONの結果がほしいRequestか否か。
	 * @param request
	 * @return true=JSONの結果ほしい
	 */
	public static boolean isJson(HttpServletRequest request) {
		return request.getRequestURI().endsWith("." + AppConfig.get("jsonSuffix"));
	}

	/**
	 * Ajax方式のRequestか否か
	 * @param request
	 * @return true = Ajax方式
	 */
	public static boolean isAjax(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	}

	/**
	 * ログイン情報を取得する。
	 * @param request
	 * @return
	 */
	public static User getUser() {

		HttpSession session = getSession();

		if (session == null) {
			return null;// will happen when no request.
		}

		User user = (User) session.getAttribute(KEY_USER);
		if (user == null) {
			// when session re-created.
			user = new User();
			session.setAttribute(KEY_USER, user);
		}

		return user;
	}

	/**
	 * SpringのScopeを利用しない。自分で管理。
	 * @param session
	 * @param user
	 */
	public static void setUser(User user) {
		HttpSession session = getSession();
		if (session != null) {
			session.setAttribute(KEY_USER, user);
		}
	}

	/**
	 * 如果以redirect:开头，则判断为redirect。
	 * @param path
	 * @return
	 */
	public static boolean isRedirect(String path) {
		return REDIRECT_URL_PREFIX.equals(getPrefix(path));
	}

	/**
	 * forward:或redirect:の存在を判別する。
	 * @param viewName
	 * @return true：有り
	 */
	public static boolean hasPrefix(String viewName) {
		return !StringUtils.isEmpty(getPrefix(viewName));
	}

	/**
	 * 取forward:或redirect:
	 * @param name
	 * @return
	 */
	public static String getPrefix(String viewName) {

		if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
			return REDIRECT_URL_PREFIX;
		}

		if (viewName.startsWith(FORWARD_URL_PREFIX)) {
			return FORWARD_URL_PREFIX;
		}

		return "";
	}

	/**
	 * リクエスト、セッションの順位で属性を取得する。
	 * @param key
	 * @return
	 */
	public static Object getAttribute(String key) {

		Object value = (Exception) getRequestAttributes().getAttribute(key, SCOPE_REQUEST);
		if (value != null) {
			return value;
		}

		return getRequestAttributes().getAttribute(key, SCOPE_SESSION);
	}

	/**
	 * セッションを取得する。
	 * @return
	 */
	private static HttpSession getSession() {
		try {
			// TODO getSession(true) or getSession(false) ? when session timeout
			return getRequestAttributes().getRequest().getSession(true);
		} catch (IllegalStateException e) {
			return null;
		}
	}

	/**
	 * 属性を取得する。<br>
	 * 注意：JSP画面では全部クリアされたから何もない。
	 * @return
	 */
	public static ServletRequestAttributes getRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	}

	private static final int retry = 5;
	private static final int timeout = 60 * 1000;
	private static final Log log = LogFactory.getLog(WebUtil.class);

	private WebUtil() {
	}
}
