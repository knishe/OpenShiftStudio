package cn.clxy.studio.common.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleInterceptor extends LocaleChangeInterceptor {

	private Locale defaultLocale = Locale.ENGLISH;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws ServletException {

		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String newLocale = request.getParameter(getParamName());

		if (newLocale == null) {
			Locale locale = localeResolver.resolveLocale(request);
			newLocale = locale.getLanguage();
		}

		Locale locale = getLocate(newLocale);
		localeResolver.setLocale(request, response, locale);

		return true;
	}

	public void setDefaultLocale(String defaultLocaleKey) {
		this.defaultLocale = getLocate(defaultLocaleKey);
	}

	private Locale getLocate(String key) {
		Locale locale = stringLocale.get(key);
		return (locale == null) ? defaultLocale : locale;
	}

	enum Langs {
		en, zh, ja
	}

	public static final List<String> LANG_NAMES = new ArrayList<String>() {
		private static final long serialVersionUID = 1L;
		{
			for (Langs l : Langs.values()) {
				add(l.name());
			}
		}
	};

	private static final Map<String, Locale> stringLocale = new HashMap<String, Locale>() {
		private static final long serialVersionUID = 1L;
		{
			put(Langs.zh.name(), Locale.CHINA);
			put(Langs.ja.name(), Locale.JAPAN);
			put(Langs.en.name(), Locale.ENGLISH);
		}
	};
}
