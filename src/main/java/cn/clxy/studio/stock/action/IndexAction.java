package cn.clxy.studio.stock.action;

import javax.annotation.Resource;

import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.apps.action.AppAction;
import cn.clxy.studio.common.action.NoAuth;
import cn.clxy.studio.stock.service.StockService;

/**
 * 入口。
 * @author clxy
 */
@NoAuth
public class IndexAction extends AppAction {

	@Resource
	protected StockService stockService;

	@Override
	public ModelAndView index() {

		ModelAndView result = super.index();
		// result.addObject(KEY_STATISTICS, statistics);
		return result;
	}

	@Override
	public Object getAppData() {
		return stockService.getAppData();
	}
}
