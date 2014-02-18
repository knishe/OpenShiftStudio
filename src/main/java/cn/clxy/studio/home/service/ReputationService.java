package cn.clxy.studio.home.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import cn.clxy.studio.common.dao.UtilDao;
import cn.clxy.studio.home.dao.ReputationDao;
import cn.clxy.studio.home.data.Reputation;

public class ReputationService {

	private Map<String, Reputation> datas = new HashMap<>();

	@Resource
	protected UtilDao utilDao;
	@Resource
	protected ReputationDao reputationDao;

	/**
	 * 更新各个网站的声望信息。
	 */
	public void refresh() {

		Map<String, Reputation> fresh = getFromSite();
		if (fresh == null || fresh.isEmpty()) {
			return;
		}

		save(fresh);
		datas = fresh;
	}

	/**
	 * 从DB里取。
	 * @return
	 */
	public Map<String, Reputation> getFromDB() {

		datas = new HashMap<>();

		for (Site s : Site.values()) {
			String site = s.name();
			Reputation data = reputationDao.findBySite(site);
			datas.put(site, data);
		}

		return datas;
	}

	/**
	 * 从网站中取。
	 * @return
	 */
	private Map<String, Reputation> getFromSite() {

		Site[] sites = Site.values();
		Map<String, Reputation> result = new HashMap<String, Reputation>(sites.length);
		for (Site s : Site.values()) {
			Reputation data = s.fetch();
			if (data.isEmpty()) {
				continue;
			}
			result.put(s.name(), data);
		}
		return result;
	}

	private void save(Map<String, Reputation> fresh) {

		for (Entry<String, Reputation> e : fresh.entrySet()) {

			Reputation curData = datas.get(e.getKey());
			Reputation newData = e.getValue();
			if (newData.isSame(curData)) {
				continue;
			}
			utilDao.insert(newData);
		}
	}

	static enum Site {

		iteye {
			@Override
			public Reputation fetch() {

				Reputation reputation = super.fetch();
				try {
					Document document = get("http://clxy.iteye.com/blog/answered_problems");
					if (document == null) {
						return reputation;
					}
					reputation.setReputation(document.select("th:contains(问答积分)+td").text());
					reputation.setGold(document.select("span.gold.gray").text());
					reputation.setSilver(document.select("span.silver.gray").text());
					reputation.setBronze(document.select("span.bronze.gray").text());
				} catch (Exception e) {
					log.debug(e);
				}
				return reputation;
			}
		},
		stackoverflow {
			@Override
			public Reputation fetch() {

				Reputation reputation = super.fetch();
				Document document = get("http://stackoverflow.com/users/2541318/clxy");
				if (document == null) {
					return reputation;
				}
				Elements element = document.select(".user-header-left .gravatar");
				reputation.setReputation(element.select(".reputation a").text());
				reputation.setGold(element.select(".badge1+.badgecount").text());
				reputation.setSilver(element.select(".badge2+.badgecount").text());
				reputation.setBronze(element.select(".badge3+.badgecount").text());
				return reputation;
			}
		};

		public Reputation fetch() {
			return new Reputation(name());
		}

		protected Document get(String url) {
			try {
				return Jsoup
						.connect(url)
						.userAgent(
								"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:27.0) Gecko/20100101 Firefox/27.0")
						.get();
			} catch (Exception e) {
				log.debug(e);
				return null;
			}
		}
	}

	private static final Log log = LogFactory.getLog(ReputationService.class);
}
