package cn.clxy.studio.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import cn.clxy.studio.common.exception.SystemException;

/**
 * Commons beanutilとSpringなどラッパーする。
 * @author clxy
 */
public final class BeanUtil {

	public static Object deserialize(byte[] objectData) {
		ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
		return deserialize(bais);
	}

	public static byte[] serialize(Serializable obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bufferSize);
		serialize(obj, baos);
		return baos.toByteArray();
	}

	/**
	 * 封装commons。主要为了异常。
	 * @param obj
	 * @param name
	 * @return
	 */
	public static Object getProperty(Object obj, String name) {
		try {
			return PropertyUtils.getProperty(obj, name);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * Hash値を生成する。
	 * @param obj
	 * @return
	 */
	public static int hash(Object obj) {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((obj == null) ? 0 : obj.hashCode());
		return result;
	}

	/**
	 * @deprecated {@link java.util.Objects#equals(Object, Object)}
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean equal(Object src, Object dest) {

		if (src == dest) {
			return true;
		}

		if (src == null || dest == null) {
			return false;
		}

		return src.equals(dest);
	}

	/**
	 * 指定ObjectをCopyする。Deepではない。
	 * @param t
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copy(T t) {

		T result = null;
		try {
			result = (T) t.getClass().newInstance();
		} catch (Exception e) {
			throw new SystemException(e);
		}

		copy(t, result);
		return result;
	}

	/**
	 * Object間をCopyする。Deepではない。
	 * @param from
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	public static void copy(Object from, Object to) {

		try {
			if (to instanceof Map) {
				((Map<String, Object>) to).putAll(toMap(from));
			} else {
				BeanUtils.copyProperties(to, from);
			}
		} catch (Exception e) {
			throw new SystemException(e);
		}

	}

	/**
	 * Mapへ変換する。
	 * @param bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object bean) {

		if (bean instanceof Map) {
			return (Map<String, Object>) bean;
		}

		try {
			return (Map<String, Object>) PropertyUtils.describe(bean);
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Object deserialize(InputStream inputStream) {

		try (ObjectInputStream in = new ObjectInputStream(inputStream)) {
			return in.readObject();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static void serialize(Serializable obj, OutputStream outputStream) {

		try (ObjectOutputStream out = new ObjectOutputStream(outputStream)) {
			out.writeObject(obj);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private static final int bufferSize = 1024 * 256;

	private BeanUtil() {
	}
}
