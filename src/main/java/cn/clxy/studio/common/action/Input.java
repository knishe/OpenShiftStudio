package cn.clxy.studio.common.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 画面をサブミットされた場合、入力元画面を明記する。
 * @author clxy
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Input {

	/**
	 * 入力画面。
	 * @return
	 */
	String value();
}
