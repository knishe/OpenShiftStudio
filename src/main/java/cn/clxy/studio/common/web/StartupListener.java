package cn.clxy.studio.common.web;

import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 起動が全部終了後関連処理。
 * @author clxy
 */
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	private static final Log log = LogFactory.getLog(StartupListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("============-----------------------After initialized.");
		TimeZone plus8 = TimeZone.getTimeZone("GMT+8");
		TimeZone.setDefault(plus8);
	}
}
