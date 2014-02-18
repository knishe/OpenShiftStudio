package cn.clxy.studio.common.data;

import java.util.HashMap;
import java.util.Map;

public class MapData<K, V> extends HashMap<K, V> {

	public MapData() {
		super();
	}

	public MapData(K key, V value) {
		this();
		put(key, value);
	}

	@SuppressWarnings("unchecked")
	public MapData(V value) {
		this();
		put((K) defaultKey, value);
	}

	public MapData(Map<? extends K, ? extends V> m) {
		super(m);
	}

	private static final String defaultKey = "result";
	private static final long serialVersionUID = 1L;
}
