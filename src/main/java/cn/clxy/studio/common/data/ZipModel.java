package cn.clxy.studio.common.data;

import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.common.AppConfig;

public class ZipModel extends ModelAndView {

	public ZipModel() {
		this(null, null);
	}

	public <K, V> ZipModel(K key, V value) {

		setViewName(AppConfig.get("zipExt"));
		addObject(AppConfig.VIEW_DATA, new MapData<>());
		if (key != null) {
			put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	public <K, V> void put(K key, V value) {
		Map<K, V> map = (Map<K, V>) getModelMap().get(AppConfig.VIEW_DATA);
		map.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <K, V> void putAll(Map<K, V> values) {
		Map<K, V> map = (Map<K, V>) getModelMap().get(AppConfig.VIEW_DATA);
		map.putAll(values);
	}
}
