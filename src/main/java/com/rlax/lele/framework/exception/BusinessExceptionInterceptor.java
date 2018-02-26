package com.rlax.lele.framework.exception;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.rlax.lele.framework.web.JsonResult;
import io.jboot.exception.JbootException;
import io.jboot.web.controller.JbootController;

/**
 * 业务异常拦截器
 * @author Rlax
 *
 */
public class BusinessExceptionInterceptor implements Interceptor {

	/** 异常内容在模版引擎中的属性TAG */
	public final static String MESSAGE_TAG = "message";

	/** 异常页面 */
	private String exceptionView = "/exception.html";

	private boolean ifJsonMode = false;

	public BusinessExceptionInterceptor(boolean ifJsonMode) {
		this.ifJsonMode = ifJsonMode;
	}

	public BusinessExceptionInterceptor(String exceptionView) {
		this.exceptionView = exceptionView;
	}

	@Override
	public void intercept(Invocation inv) {
		try {
			inv.invoke();
		} catch (JbootException e) {
			if (inv.getTarget() instanceof JbootController) {
				JbootController controller = inv.getTarget();

				if (ifJsonMode || controller.isAjaxRequest()) {
					controller.renderJson(JsonResult.buildError(e.getMessage()));
				} else {
					controller.setAttr(MESSAGE_TAG, e.getMessage()).render(exceptionView);
				}
			}
		}
	}

}
