package cn.clxy.studio.common.util;

import java.util.Collection;
import java.util.Iterator;

public final class StringUtil {

	public static boolean isEmpty(String string) {
		return string == null || string.length() == 0;
	}

	public static String join(Collection<?> list, String sep) {

		Iterator<?> it = list.iterator();
		if (!it.hasNext()) {
			return "";
		}

		String start = it.next().toString();
		if (!it.hasNext()) {
			return start;
		}

		StringBuilder sb = new StringBuilder(64).append(start);
		while (it.hasNext()) {
			sb.append(sep);
			sb.append(it.next());
		}
		return sb.toString();
	}

	private StringUtil() {
	}
}
