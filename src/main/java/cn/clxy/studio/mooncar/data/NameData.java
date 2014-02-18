package cn.clxy.studio.mooncar.data;

import java.util.HashMap;
import java.util.Map;

import cn.clxy.studio.common.data.BaseData;

public class NameData extends BaseData {

	private String name;
	private int count;

	private Map<String, OriginNameData> origins = new HashMap<>();

	public NameData() {
	}

	public NameData(String name) {
		this.name = name;
	}

	public void countUp() {
		this.count++;
	}

	public void mergeOrigin(OriginNameData origin) {

		String key = origin.getSite();
		OriginNameData sameSite = origins.get(key);
		if (sameSite == null || sameSite.getOriginId() > origin.getOriginId()) {
			origins.put(key, origin);
		}
	}

	public Map<String, OriginNameData> getOrigins() {
		return origins;
	}

	public void setOrigins(Map<String, OriginNameData> origins) {
		this.origins = origins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private static final long serialVersionUID = 1L;
}
