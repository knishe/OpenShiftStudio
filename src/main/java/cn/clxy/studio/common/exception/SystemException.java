package cn.clxy.studio.common.exception;

/**
 * システム例外。
 * @author clxy
 */
public class SystemException extends BaseException {

	/**
	 * デフォルト。
	 */
	public SystemException() {
		this(null, null);
	}

	/**
	 * 指定メッセージの例外。<br>
	 * TODO DBレイヤかサービス階層か指定する時、必要？
	 * @param code
	 * @param params
	 */
	public SystemException(String code, Object[] params) {
		super(code, params);
		view = page;
	}

	public SystemException(Exception e) {
		super(e);
		view = page;
	}

	private static final String page = "common/systemException";
	private static final long serialVersionUID = 1L;
}
