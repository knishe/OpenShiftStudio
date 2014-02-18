package cn.clxy.studio.common.exception;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public abstract class BaseException extends RuntimeException {

	/**
	 * エラー情報。
	 */
	private ObjectError error;

	/**
	 * 例外時遷移先。
	 */
	protected String view;

	public BaseException(Throwable cause) {
		super(cause);
		Object[] params = null;
		setError(null, params);
	}

	public BaseException(String code, Object... params) {
		super();
		setError(code, params);
	}

	public BaseException(String code, Throwable cause, Object... params) {
		super(cause);
		setError(code, params);
	}

	public ObjectError getError() {
		return error;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	/**
	 * Spring利用できるエラー情報を生成する。
	 * @param code
	 * @param params
	 */
	protected void setError(String code, Object... params) {

		code = (code == null) ? "exception.system.default" : code;
		error = new FieldError(blank, blank, null, false,
				new String[] { code }, params, "{" + code + "}");
	}

	private static final String blank = "";
	private static final long serialVersionUID = 1L;
}
