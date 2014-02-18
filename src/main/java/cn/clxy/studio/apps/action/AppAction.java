package cn.clxy.studio.apps.action;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.common.action.Layout;
import cn.clxy.studio.common.action.NoAuth;

/**
 * App Action的父类。<br>
 * <ul>
 * <li>首页=index，需要该模块下有index.jsp。
 * <li>缩略=thumbnail，需要该模块下有thumbnail.jsp。
 * <li>模块数据=getAppData，返回该模块描述数据。
 * </ul>
 * I hate inheritance!
 * @author clxy
 */
@NoAuth
public abstract class AppAction {

	@RequestMapping()
	public ModelAndView index() {
		return new ModelAndView(PAGE_INDEX);
	}

	@Layout(none = true)
	@RequestMapping(PAGE_THUMBNAIL)
	public ModelAndView thumbnail() {
		return new ModelAndView(PAGE_THUMBNAIL);
	}

	@ModelAttribute(KEY_APP)
	public abstract Object getAppData();

	protected static final String KEY_APP = "app";
	protected static final String PAGE_INDEX = "index";
	protected static final String PAGE_THUMBNAIL = "thumbnail";
}
