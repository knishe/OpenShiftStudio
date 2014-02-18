package cn.clxy.studio.common.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.clxy.studio.common.action.ActionUtil;
import cn.clxy.studio.common.action.Layout;
import cn.clxy.studio.common.web.WebUtil;

/**
 * Viewの関連処理。
 * <ol>
 * <li>入力元とパッケージパスを保存する。
 * <li>パッケージのURLを追加する。
 * <li>レイアウトを設定する。簡単実現なので、複雑になったら、Tilesを取り入る方向へ。
 * </ol>
 * @author clxy
 */
public class ViewInterceptor extends HandlerInterceptorAdapter {

	private String defaultLayout;

	/**
	 * 入力元とパッケージパスを保存する。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		if (!HandlerMethod.class.isInstance(handler)) {
			return true;
		}

		HandlerMethod method = (HandlerMethod) handler;
		log.warn("Start " + method);

		ActionUtil.saveCurrentInput(method);
		ActionUtil.saveModuleUrl(method);

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView view) throws Exception {

		log.warn("Done " + handler);

		if (view == null) {
			return;
		}

		String result = view.getViewName();
		if (result == null || hasExt(result) || !HandlerMethod.class.isInstance(handler)) {
			// 有扩展名时都会走共通。比如json或xls，所以不处理。
			return;
		}

		HandlerMethod method = (HandlerMethod) handler;

		// module路径処理。
		result = ActionUtil.addModuleUrl(method, result);
		view.setViewName(result);

		// Layout処理。
		processLayout(method, view);
	}

	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

	/**
	 * 下記の場合、レイアウト使用しない。
	 * <ul>
	 * <li>遷移しない。
	 * <li>redirect:とforward:がある。
	 * <li>Annotationでレイアウト不要指定された場合。
	 * </ul>
	 * @param method
	 * @param view
	 */
	private void processLayout(HandlerMethod method, ModelAndView view) {

		String name = view.getViewName();
		if (WebUtil.hasPrefix(name)) {
			return;
		}

		String layoutName = defaultLayout;
		Layout annotation = ActionUtil.getLayout(method);
		if (annotation != null) {
			if (annotation.none()) {
				return;
			}
			layoutName = annotation.view();
		}

		name = WebUtil.getJspUrl(name);
		view.addObject(subviewName, name);
		view.setViewName(layoutName);
	}

	/**
	 * 拡張子有りか判別。
	 * @param view
	 * @return true → 有り
	 */
	private boolean hasExt(String view) {
		return view.indexOf('.') >= 0;
	}

	private final static String subviewName = "subview";
	private static final Log log = LogFactory.getLog(ViewInterceptor.class);
}
