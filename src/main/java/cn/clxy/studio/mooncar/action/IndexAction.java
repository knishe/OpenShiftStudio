package cn.clxy.studio.mooncar.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.apps.action.AppAction;
import cn.clxy.studio.common.action.Layout;
import cn.clxy.studio.common.data.PaginationData;
import cn.clxy.studio.mooncar.data.NameData;
import cn.clxy.studio.mooncar.data.StatisticsData;
import cn.clxy.studio.mooncar.service.MooncarService;

/**
 * 入口。
 * @author clxy
 */
@SessionAttributes({
		IndexAction.KEY_ALL_NAMES, IndexAction.KEY_PAGINATION, IndexAction.KEY_STATISTICS })
public class IndexAction extends AppAction {

	@Resource
	protected MooncarService mooncarService;

	@Override
	public ModelAndView index() {

		ModelAndView result = super.index();
		result.addObject(KEY_STATISTICS, mooncarService.getStatistics())
				.addObject("topCount", mooncarService.getTopCount());

		return result;
	}

	@RequestMapping("search")
	@Layout(none = true)
	public ModelAndView search(@RequestParam(value = "name", required = false) String name) {

		List<NameData> allNames = mooncarService.search(name);
		PaginationData pageData = new PaginationData(allNames.size());

		ModelAndView result = new ModelAndView("namesTable");
		result.addObject(KEY_ALL_NAMES, allNames);
		result.addObject(KEY_PAGINATION, pageData);
		return result;
	}

	@RequestMapping("page")
	@Layout(none = true)
	public String page(
			@ModelAttribute(KEY_PAGINATION) PaginationData pageData,
			@ModelAttribute(KEY_ALL_NAMES) List<NameData> allNames) {
		return "namesTable";
	}

	@RequestMapping("statistics")
	@Layout(none = true)
	public String statistics() {
		return "panelStatistics";
	}

	@ModelAttribute(KEY_PAGINATION)
	public PaginationData createPagination() {
		return new PaginationData();
	}

	@ModelAttribute(KEY_ALL_NAMES)
	public List<NameData> createNameList() {
		return new ArrayList<>();
	}

	@ModelAttribute(KEY_STATISTICS)
	public StatisticsData getStatistics() {
		return mooncarService.getStatistics();
	}

	@Override
	public Object getAppData() {
		return mooncarService.getAppsData();
	}

	public static final String KEY_STATISTICS = "statistics";
	public static final String KEY_ALL_NAMES = "allNames";
	public static final String KEY_PAGINATION = "pagination";
}
