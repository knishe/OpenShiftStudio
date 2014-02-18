package cn.clxy.studio.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.StringValueResolver;

/**
 * 設定を管理する。<br>
 * Springから設定を同期する。
 * @author clxy
 */
public final class AppConfig extends PropertyPlaceholderConfigurer {

	public static final String VIEW_DATA = "view.data";
	public static final String VIEW_CHARSET = "view.charset";

	public static final String KEY_INPUT = "system.action.input";
	/**
	 * 因为在Jsp的tag中使用，所以命名方式采用大小写。
	 * @see res.tag
	 */
	public final static String KEY_MODULE_URL = "moduleUrl";

	public final static String SLASH = "/";

	/**
	 * app.propertiesに定義する属性。
	 */
	private static Map<String, String> properties = new HashMap<>();

	/**
	 * 默认每页件数。
	 * @return
	 */
	public static int getDefaultLimit() {
		return Integer.parseInt(get("defaultLimit"));
	}

	/**
	 * 設定を取得する。
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return properties.get(key);
	}

	public static int getInt(String key) {
		String v = properties.get(key);
		return Integer.parseInt(v);
	}

	public static boolean getBoolean(String key) {
		String v = properties.get(key);
		return Boolean.parseBoolean(v);
	}

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {

		properties.clear();
		for (Entry<Object, Object> e : props.entrySet()) {
			properties.put(e.getKey().toString(), e.getValue().toString());
		}

		super.processProperties(beanFactory, props);
	}

	@Override
	protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			StringValueResolver valueResolver) {

		super.doProcessProperties(beanFactoryToProcess, valueResolver);

		for (Entry<String, String> e : properties.entrySet()) {
			e.setValue(valueResolver.resolveStringValue(e.getValue()));
		}
	}

	private AppConfig() {
	}
}
