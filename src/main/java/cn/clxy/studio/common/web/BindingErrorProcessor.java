package cn.clxy.studio.common.web;

import javax.annotation.Resource;

import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DefaultBindingErrorProcessor;
import org.springframework.validation.FieldError;

/**
 * 変換エラーのメッセージ関連処理。
 * @author clxy
 */
public class BindingErrorProcessor extends DefaultBindingErrorProcessor {

	@Resource
	private MessageSource messageSource;

	@Override
	public void processPropertyAccessException(PropertyAccessException ex,
			BindingResult bindingResult) {

		// Create field error with the exceptions's code, e.g. "typeMismatch".
		String field = ex.getPropertyName();
		String[] codes = bindingResult.resolveMessageCodes(ex.getErrorCode(), field);
		Object[] arguments = getArgumentsForBindError(bindingResult.getObjectName(), field);
		Object rejectedValue = ex.getValue();
		if (rejectedValue != null && rejectedValue.getClass().isArray()) {
			rejectedValue = StringUtils.arrayToCommaDelimitedString(ObjectUtils
					.toObjectArray(rejectedValue));
		}

		// clxy TypeMismatchExceptionの場合、MessageSourceから取得する。
		String message = ex.getLocalizedMessage();
		if (ex instanceof TypeMismatchException) {
			String code = "typeMismatch."
					+ ((TypeMismatchException) ex).getRequiredType().getName();
			message = messageSource.getMessage(code, null, WebUtil.getLocale());
		}

		bindingResult.addError(new FieldError(
				bindingResult.getObjectName(), field, rejectedValue, true,
				codes, arguments, message));
	}
}
