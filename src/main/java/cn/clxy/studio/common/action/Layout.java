package cn.clxy.studio.common.action;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.clxy.studio.common.aop.ViewInterceptor;

/**
 * メソッドのLayout情報。
 * <ul>
 * <li>設定していない場合、デフォルトのレイアウトを利用する。
 * <li>@Layout(none=true)を設定すると、レイアウトを利用しない。
 * <li>例：@Layout(view='/common/otherLayout')を設定すると、指定レイアウトを利用する。
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
@Documented
public @interface Layout {

	/**
	 * レイアウトのパス。
	 * @see ViewInterceptor
	 * @return
	 */
	String view() default "";

	/**
	 * レイアウト必要か否か。
	 * @see ViewInterceptor
	 * @return false 不要。
	 */
	boolean none() default false;
}
