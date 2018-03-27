package com.rlax.lele.framework.web;

import com.jfinal.kit.Ret;

/**
 * json result
 * @author Rlax
 *
 */
public class JsonResult {

    /** 成功 */
    public final static String SUCCESS_CODE = "1";

    /** 校验异常 */
    public final static String VALIDATOR_ERROR_CODE = "99";

    /** 业务异常 */
    public final static String BUSINESS_ERROR_CODE = "100";

    public final static String MESSAGE = "message";
    public final static String CODE = "code";
    public final static String DATA = "data";

    public static Ret buildSuccess() {
        return Ret.ok(MESSAGE, "操作成功").set(CODE, SUCCESS_CODE);
    }

    public static Ret buildSuccess(String msg) {
        return Ret.ok(MESSAGE, msg).set(CODE, SUCCESS_CODE);
    }

    public static Ret buildSuccess(Object t) {
        return buildSuccess().set(DATA, t);
    }

    public static Ret buildSuccess(String msg, Object t) {
        return buildSuccess(msg).set(DATA, t);
    }

    public static Ret buildError() {
        return Ret.fail(MESSAGE, "操作失败").set(CODE, BUSINESS_ERROR_CODE);
    }

    public static Ret buildError(String msg) {
        return Ret.fail(MESSAGE, msg).set(CODE, BUSINESS_ERROR_CODE);
    }

    public static Ret buildError(String code, String msg) {
        return Ret.fail(MESSAGE, msg).set(CODE, code);
    }
}
