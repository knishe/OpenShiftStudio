package cn.clxy.studio.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * パッケージ関連ツール。
 * @author clxy
 */
public final class PackageUtil {

	/**
	 * package名称最后一段。比如java.lang -> lang。
	 * @param p
	 * @return
	 */
	public static String getShortName(Package p) {
		String name = p.getName();
		int index = name.lastIndexOf('.');
		if (index < 0) {
			return name;
		}
		return name.substring(index);
	}

	/**
	 * 親パッケージの名前を取得する。
	 * @param name
	 * @return
	 */
	public static String getParentName(String name) {

		int index = name.lastIndexOf('.');
		return index < 0 ? null : name.substring(0, index);
	}

	/**
	 * 寻找指定package的父package。
	 * @param p
	 * @return
	 */
	public static Package getParent(Package p) {

		String name = getParentName(p.getName());

		while (name != null) {

			// 用名字查询package。
			Package parent = Package.getPackage(name);
			if (parent == null) {// 没有查到时，尝试加载Package-info。
				try {
					Class.forName(name + ".package-info");
				} catch (ClassNotFoundException e) {
					log.debug("Can not find " + name + ".package-info");
				}
				// 再次用名字查询package。
				parent = Package.getPackage(name);
			}

			if (parent != null) {
				return parent;
			}
			name = getParentName(name);
		}

		return null;
	}

	private PackageUtil() {
	}

	private static final Log log = LogFactory.getLog(PackageUtil.class);
}
