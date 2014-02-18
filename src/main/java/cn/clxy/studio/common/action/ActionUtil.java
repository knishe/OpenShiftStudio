package cn.clxy.studio.common.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import cn.clxy.studio.common.AppConfig;
import cn.clxy.studio.common.util.AnnotationUtil;
import cn.clxy.studio.common.util.AppUtil;
import cn.clxy.studio.common.web.WebUtil;

/**
 * 処理メソッド関連ツール。<br>
 * 高速化するため、起動時すべてメソッドを登録する。<br>
 * @see ActionHandler#getMappingForMethod
 * @author clxy
 */
public final class ActionUtil {

	/**
	 * 追加module路径。
	 * <ul>
	 * <li>如果有指定forward:或redirect:，取后面的字符串。
	 * <li>如果是以/开头认定是绝对路径。不处理。
	 * <li>追加module路径。
	 * </ul>
	 * 转向jsp时，不可以加forward:或redirect:！！！
	 * @param method
	 * @param path
	 * @return
	 */
	public static String addModuleUrl(HandlerMethod method, String path) {
		return addModuleUrl(ActionUtil.getModuleUrl(method), path);
	}

	/**
	 * 追加module路径。
	 * <ul>
	 * <li>如果有指定forward:或redirect:，取后面的字符串。
	 * <li>如果是以/开头认定是绝对路径。不处理。
	 * <li>追加module路径。
	 * </ul>
	 * 转向jsp时，不可以加forward:或redirect:！！！
	 * @param moduleUrl
	 * @param path
	 * @return
	 */
	static String addModuleUrl(String moduleUrl, String path) {

		if (moduleUrl == null) {
			return path;
		}

		String url = path;
		// 接頭辞を切り取る。
		String prefix = WebUtil.getPrefix(url);
		url = url.substring(prefix.length());

		// 绝对路径不处理。
		String slash = AppConfig.SLASH;
		if (url.startsWith(slash)) {
			return path;
		}

		url = moduleUrl + slash + url;// 追加module路径。
		url = prefix + url;// 接頭辞を戻す。

		return url;
	}

	/**
	 * 保存module的路径信息。
	 * @param method
	 */
	public static void saveModuleUrl(HandlerMethod method) {

		String moduleUrl = ActionUtil.getModuleUrl(method);
		if (moduleUrl == null) {
			return;
		}
		getAttributes().setAttribute(
				AppConfig.KEY_MODULE_URL, moduleUrl, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 入力元をRequestに保存する。
	 * @param method
	 */
	public static void saveCurrentInput(HandlerMethod method) {
		String input = getInput(method);
		getAttributes().setAttribute(
				AppConfig.KEY_INPUT, input, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 入力元を取得する。
	 * @return
	 */
	public static String getCurrentInput() {
		return (String) getAttributes().getAttribute(
				AppConfig.KEY_INPUT, RequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * メソッドのパッケージURLを取得する。
	 * @param method
	 * @return
	 */
	public static String getModuleUrl(HandlerMethod method) {
		return getInfo(method).moduleUrl;
	}

	/**
	 * メソッドの入力元を取得する。
	 * @param method
	 * @return
	 */
	public static String getInput(HandlerMethod method) {
		return getInfo(method).input;
	}

	/**
	 * メソッドのレイアウトを取得する。
	 * @param method
	 * @return
	 */
	public static Layout getLayout(HandlerMethod method) {
		return getInfo(method).layout;
	}

	/**
	 * 简化写法。
	 * @return
	 */
	private static ServletRequestAttributes getAttributes() {
		return WebUtil.getRequestAttributes();
	}

	private static ActionInfo getInfo(HandlerMethod method) {

		ActionInfo info = actionMapping.get(method);
		if (info != null) {
			return info;
		}

		info = new ActionInfo(method);
		actionMapping.put(method, info);
		return info;
	}

	/**
	 * 処理メソッドの関連情報。
	 * @author clxy
	 */
	private static class ActionInfo {

		/**
		 * パッケージのURL。
		 */
		public String moduleUrl;

		/**
		 * 入力元。
		 */
		public String input;

		/**
		 * レイアウト情報。
		 */
		public Layout layout;

		public ActionInfo(HandlerMethod method) {
			moduleUrl = AppUtil.getModuleName(method.getBeanType())[0];
			layout = AnnotationUtil.find(method, Layout.class);
			initInput(method);
		}

		private void initInput(HandlerMethod method) {
			Input a = AnnotationUtil.find(method, Input.class);
			if (a != null) {
				input = ActionUtil.addModuleUrl(moduleUrl, a.value());
			}
		}
	}

	/**
	 * メソッド情報のケッシュ。
	 */
	private static final Map<HandlerMethod, ActionInfo> actionMapping = new HashMap<>();

	private ActionUtil() {
	}
}
