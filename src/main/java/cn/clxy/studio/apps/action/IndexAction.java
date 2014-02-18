package cn.clxy.studio.apps.action;

import javax.annotation.Resource;

import cn.clxy.studio.apps.service.AppsService;

/**
 * 入口。
 * @author clxy
 */
public class IndexAction extends AppAction {

	@Resource
	protected AppsService appsService;

	@Override
	public Object getAppData() {
		return appsService.getApps();
	}
}