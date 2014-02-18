package cn.clxy.studio.stock.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.clxy.studio.apps.data.AppsData;
import cn.clxy.studio.apps.service.AppsService;

public class StockService {

	public static final String APP_ID = "stock";
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
