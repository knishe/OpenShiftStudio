package cn.clxy.studio.common.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TODO 要这个，还是要分成三个Input, NoAuth, Layout？<br>
 * @deprecated 还是三个灵活些@20131110。
 * @author clxy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Action {

	/**
	 * 入力元画面。
	 * @return
	 */
	String input();

	/**
	 * @return false时则无须验证。
	 */
	boolean auth() default true;

	/**
	 * @return empty = ""时使用默认布局；null时则无须布局。
	 */
	String layout() default "";
}
