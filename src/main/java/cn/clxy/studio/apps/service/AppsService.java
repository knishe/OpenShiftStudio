package cn.clxy.studio.apps.service;

import java.util.ArrayList;
import java.util.Collection;

import cn.clxy.studio.apps.data.AppsData;

public class AppsService {

	private static Collection<AppsData> apps = new ArrayList<>();

	public void register(AppsData app) {
		apps.add(app);
	}

	public Collection<AppsData> getApps() {
		return apps;
	}
}
