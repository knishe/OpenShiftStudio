package cn.clxy.studio.apps.data;

import java.io.Serializable;

/**
 * 应用信息。
 * @author clxy
 */
public class AppsData implements Serializable {

	private String code;
	private String thumbnail;
	private String name;
	private String description;

	public AppsData() {
	}

	public AppsData(String code) {
		this.code = code;
		this.thumbnail = "/" + code + "/thumbnail";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	private static final long serialVersionUID = 1L;
}
