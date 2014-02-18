package cn.clxy.studio.common.exception;

public class ServiceException extends BaseException {

	public ServiceException(String code) {
		super(code, new Object[] { null });
	}

	public ServiceException(String code, Object... params) {
		super(code, params);
	}

	public ServiceException(String code, Throwable cause, Object... params) {
		super(code, params, cause);
	}

	private static final long serialVersionUID = 1L;
}
