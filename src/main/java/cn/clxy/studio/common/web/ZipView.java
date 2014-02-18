package cn.clxy.studio.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import cn.clxy.studio.common.AppConfig;
import cn.clxy.studio.common.util.ZipUtil;

public class ZipView extends AbstractView {

	public static final String ZIP_FILE_NAME = "view.zip.file";

	public ZipView() {
		setContentType(contentType);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> data = (Map<String, Object>) model.get(AppConfig.VIEW_DATA);
		// String charset = (String) model.get(AppConfig.VIEW_CHARSET);
		String fileName = (String) model.get(ZIP_FILE_NAME);
		if (fileName == null) {
			fileName = defaultZipName;
		}

		response.setContentType(contentType);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + ".zip\"");

		ZipUtil.zip(response.getOutputStream(), data);
	}

	private static final String defaultZipName = "download";
	private static final String contentType = "application/x-zip-compressed";
}
