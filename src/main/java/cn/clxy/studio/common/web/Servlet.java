package cn.clxy.studio.common.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.servlet.DispatcherServlet;

public class Servlet extends DispatcherServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		WebUtil.servletContext = config.getServletContext();
		super.init(config);
	}

	private static final long serialVersionUID = 1L;
}
