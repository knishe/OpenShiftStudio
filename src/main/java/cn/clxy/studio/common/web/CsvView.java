package cn.clxy.studio.common.web;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cn.clxy.studio.common.AppConfig;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvView extends AbstractView {

	public static final String CSV_CLASS = "view.csv.class";

	public CsvView() {
		setContentType(contentType);
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Class<?> clazz = (Class<?>) model.get(CSV_CLASS);
		Collection<?> datas = (Collection<?>) model.get(AppConfig.VIEW_DATA);
		String charset = (String) model.get(AppConfig.VIEW_CHARSET);
		charset = (charset == null) ? "utf-8" : charset;

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(clazz);

		// TODO charset对Jackson的csv不起作用的样子，它固定UTF8。
		response.setContentType(contentType + ";charset=" + charset);
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + clazz.getSimpleName() + ".csv\"");

		OutputStream bos = new BufferedOutputStream(response.getOutputStream());
		mapper.writer(schema).writeValue(bos, datas);
		bos.flush();
		bos.close();
	}

	private static final String contentType = "text/csv";
}
