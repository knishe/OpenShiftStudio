package cn.clxy.studio.mooncar.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.clxy.studio.apps.data.AppsData;
import cn.clxy.studio.apps.service.AppsService;
import cn.clxy.studio.common.exception.ServiceException;
import cn.clxy.studio.common.util.AppUtil;
import cn.clxy.studio.common.util.StringUtil;
import cn.clxy.studio.common.util.ZipUtil;
import cn.clxy.studio.common.web.WebUtil;
import cn.clxy.studio.mooncar.data.NameData;
import cn.clxy.studio.mooncar.data.OriginNameData;
import cn.clxy.studio.mooncar.data.StatisticsData;
import cn.clxy.studio.mooncar.data.StatisticsData.SiteData;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * GAE上存储条件无法满足需求，所以全部数据放在内存。
 * @author clxy
 */
public class MooncarService {

	public static final String APP_ID = "mooncar";
	private static AppsData appsData;

	static StatisticsData statistics;
	static List<NameData> names;
	static List<NameData> topCount;

	@Resource
	protected AppsService appsService;

	@PostConstruct
	public void init() {

		reset();

		appsData = new AppsData(APP_ID);
		appsService.register(appsData);

		// loadFrom();
	}

	public Integer deleteAll() {
		reset();
		return new Random().nextInt();
	}

	public List<NameData> search(String name) {

		List<NameData> result = new ArrayList<>();

		if (StringUtil.isEmpty(name)) {
			return AppUtil.getSub(names, 0, searchLimit);
		}

		for (NameData data : names) {
			if (data.getName().indexOf(name) >= 0) {
				result.add(data);
			}
		}

		return result;
	}

	public void setDatas(List<NameData> ns, StatisticsData ss) {
		names = ns;
		statistics = ss;
		arrange();
	}

	public void analyze(final List<OriginNameData> datas) {

		if (datas == null || datas.isEmpty()) {
			return;
		}

		log.warn("Latest name have " + datas.size());

		Map<String, NameData> nameMap = new HashMap<>();
		for (NameData data : names) {
			nameMap.put(data.getName(), data);
		}

		for (OriginNameData odata : datas) {

			statistics.analyzeOrigin(odata);

			for (String name : MooncarUtil.clean(odata.getName())) {
				NameData nameData = nameMap.get(name);
				if (nameData == null) {
					nameData = new NameData(name);
					nameMap.put(name, nameData);
				}
				nameData.countUp();
				nameData.mergeOrigin(odata);
			}
		}

		for (Site site : Site.values()) {
			SiteData sd = statistics.getSite(site.name());
			sd.setCountUnduplicated(0);
		}

		for (Entry<String, NameData> e : nameMap.entrySet()) {
			NameData data = e.getValue();
			statistics.analyzeName(data);
		}

		names.clear();
		names.addAll(nameMap.values());
		statistics.setUpdateAt(new Date());

		arrange();

		log.warn("Unduplicatied names have " + nameMap.size());
	}

	public void refresh() {

		// loadFrom();// load again for backend thread.

		if (statistics.getUpdateAt() == null) {
			return;// GAE上不能从头来过！
		}

		List<OriginNameData> newNames = new ArrayList<>();
		try {
			for (Site site : Site.values()) {
				site.fetch(newNames);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("exception.service.fetch.failed", e, e.getMessage());
		}

		analyze(newNames);
	}

	public void setAnalyzeData(Map<String, byte[]> data) throws Exception {

		List<NameData> names = objectMapper.readValue(
				data.get(namesZipName), AppUtil.getListType(objectMapper, NameData.class));
		StatisticsData statistics = objectMapper.readValue(
				data.get(statisticsZipName), StatisticsData.class);

		setDatas(names, statistics);
	}

	public Map<String, Object> getAnalyzeData() throws Exception {
		Map<String, Object> result = new HashMap<>();
		String ss = objectMapper.writeValueAsString(statistics);
		String sn = objectMapper.writeValueAsString(names);
		result.put(namesZipName, sn);
		result.put(statisticsZipName, ss);
		return result;
	}

	public AppsData getAppsData() {
		return appsData;
	}

	public List<NameData> getNames() {
		return names;
	}

	public StatisticsData getStatistics() {
		return statistics;
	}

	public List<NameData> getTopCount() {
		return topCount;
	}

	private void loadFrom() {

		try {
			Map<String, byte[]> contents = ZipUtil.unzip(WebUtil.load(dataFile));

			List<NameData> names = objectMapper.readValue(
					contents.get(namesZipName), AppUtil.getListType(objectMapper, NameData.class));
			StatisticsData statistics = objectMapper.readValue(
					contents.get(statisticsZipName), StatisticsData.class);

			setDatas(names, statistics);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	private void reset() {
		statistics = new StatisticsData();
		names = new ArrayList<NameData>();
		topCount = new ArrayList<NameData>();
	}

	private void arrange() {
		Collections.sort(names, countComparator);
		statistics.setCount(names.size());
		topCount = AppUtil.getSub(names, 0, topLimit);
	}

	private static final Comparator<NameData> countComparator = new Comparator<NameData>() {
		@Override
		public int compare(NameData o1, NameData o2) {
			return o2.getCount() - o1.getCount();
		}
	};

	private static final String namesZipName = "names.json";
	private static final String statisticsZipName = "statistics.json";

	private static final String dataFile =
			// "https://drive.google.com/uc?id=0ByJGzqVaVehGeE83TXVEWXpWUEk";
			"/WEB-INF/temp/analyze1027.zip";

	private static final int topLimit = 10;
	private static final int searchLimit = 500;

	private static ObjectMapper objectMapper =
			new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
	private static final Log log = LogFactory.getLog(MooncarService.class);
}
