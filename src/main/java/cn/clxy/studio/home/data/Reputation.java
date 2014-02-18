package cn.clxy.studio.home.data;

import java.beans.Transient;
import java.util.Objects;

import cn.clxy.studio.common.data.BaseData;

public class Reputation extends BaseData {

	/**
	 * 网站名称。
	 */
	private String site;
	/**
	 * 总声望。
	 */
	private String reputation;
	private String gold;
	private String silver;
	private String bronze;

	public Reputation() {
	}

	public Reputation(String site) {
		this.site = site;
	}

	public boolean isSame(Reputation other) {

		if (other == null) {
			return false;
		}

		return Objects.equals(reputation, other.getReputation())
				&& Objects.equals(gold, other.getGold())
				&& Objects.equals(silver, other.getSilver())
				&& Objects.equals(bronze, other.getBronze());
	}

	@Transient
	public boolean isEmpty() {
		return Objects.equals(null, reputation);
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getReputation() {
		return reputation;
	}

	public void setReputation(String reputation) {
		this.reputation = reputation;
	}

	public String getGold() {
		return gold;
	}

	public void setGold(String gold) {
		this.gold = gold;
	}

	public String getSilver() {
		return silver;
	}

	public void setSilver(String silver) {
		this.silver = silver;
	}

	public String getBronze() {
		return bronze;
	}

	public void setBronze(String bronze) {
		this.bronze = bronze;
	}

	private static final long serialVersionUID = 1L;
}
