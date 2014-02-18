package cn.clxy.studio.common.data;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.servlet.ModelAndView;

import cn.clxy.studio.common.AppConfig;
import cn.clxy.studio.common.web.CsvView;

public class CsvModel extends ModelAndView {

	public CsvModel() {
		setViewName(AppConfig.get("csvExt"));
	}

	@SafeVarargs
	public <T> CsvModel(Class<T> clazz, T... data) {
		this(clazz, Arrays.asList(data));
	}

	public <T> CsvModel(Class<T> clazz, Collection<T> data) {
		this();
		addObject(CsvView.CSV_CLASS, clazz);
		addObject(AppConfig.VIEW_DATA, data);
	}

	public <T> CsvModel(Class<T> clazz, Collection<T> data, String charset) {
		this(clazz, data);
		addObject(AppConfig.VIEW_CHARSET, charset);
	}
}
