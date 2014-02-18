package cn.clxy.studio.rps.action;

import javax.annotation.Resource;

import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.apps.action.AppAction;
import cn.clxy.studio.common.action.NoAuth;
import cn.clxy.studio.rps.service.RpsService;

/**
 * 入口。
 * @author clxy
 */
@NoAuth
public class IndexAction extends AppAction {

	@Resource
	protected RpsService rpsService;

	@Override
	public ModelAndView index() {

		ModelAndView result = super.index();
		// result.addObject(KEY_STATISTICS, statistics);
		return result;
	}

	@Override
	public Object getAppData() {
		return rpsService.getAppData();
	}
}
