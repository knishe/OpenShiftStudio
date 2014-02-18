package cn.clxy.studio.rps.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.clxy.studio.apps.data.AppsData;
import cn.clxy.studio.apps.service.AppsService;

public class RpsService {

	public static final String APP_ID = "rps";
	private static AppsData appsData;

	@Resource
	protected AppsService appsService;

	@PostConstruct
	public void init() {

		appsData = new AppsData(APP_ID);
		appsService.register(appsData);

	}

	public AppsData getAppData() {
		return appsData;
	}
}
