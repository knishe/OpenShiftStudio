package cn.clxy.studio.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import cn.clxy.studio.common.exception.SystemException;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * アプリケーション共通ツール。
 * @author clxy
 */
public class AppUtil {

	/**
	 * 取得RequestMapping信息。<br>
	 * TODO 所有模块必须设置RequestMapping?<br>
	 * 考虑加载Package比较慢，使用了缓存。
	 * @param p
	 * @return
	 */
	public static RequestMapping getModuleMapping(Package p) {

		if (moduleMapping.containsKey(p)) {
			return moduleMapping.get(p);
		}

		RequestMapping m = AnnotationUtil.find(p, RequestMapping.class);
		if (m == null) {
			throw new RuntimeException("Package:" + p + " hasn't RequestMapping defined!");
		}

		moduleMapping.put(p, m);
		return m;
	}

	/**
	 * 模块名取得。
	 * @see #getModuleMapping(Package)
	 * @param clazz
	 * @return
	 */
	public static String[] getModuleName(Package p) {
		RequestMapping m = getModuleMapping(p);
		return (m == null) ? null : m.value();
	}

	/**
	 * @see #getModuleName(Package)
	 * @param clazz
	 * @return
	 */
	public static String[] getModuleName(Class<?> clazz) {
		return getModuleName(clazz.getPackage());
	}

	/**
	 * @see #getModuleName(Class)
	 * @param clazzName
	 * @return
	 */
	public static String[] getModuleName(String clazzName) {
		try {
			return getModuleName(Class.forName(clazzName));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * MD5ハッシュを取得する。
	 * @param string
	 * @return
	 */
	public static String getMD5(String string) {

		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(string.getBytes());
			byte[] digest = md.digest();
			for (int i = 0; i < digest.length; i++) {
				if ((0xff & digest[i]) < 0x10) {
					sb.append("0" + Integer.toHexString((0xFF & digest[i])));
				} else {
					sb.append(Integer.toHexString(0xFF & digest[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			throw new SystemException(e);
		}

		return sb.toString();
	}

	public static <T> List<T> getSub(List<T> list, int start, int end) {

		List<T> result = new ArrayList<T>();
		if (list == null || list.isEmpty() || list.size() < start) {
			return result;
		}

		int size = list.size();
		end = Math.min(end, size);
		result.addAll(list.subList(start, end));
		return result;
	}

	public static JavaType getListType(ObjectMapper mapper, Class<?> clazz) {
		return mapper.getTypeFactory().constructParametricType(List.class, clazz);
	}

	/**
	 * パッケージ情報のケッシュ。起動時処理中心なのでMulti-Thread未対応。
	 */
	private static final Map<Package, RequestMapping> moduleMapping = new HashMap<>();
}
