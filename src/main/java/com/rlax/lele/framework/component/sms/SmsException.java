package com.rlax.lele.framework.component.sms;

/**
 * Sms 异常
 * @author Rlax
 *
 */
public class SmsException extends RuntimeException {

    public SmsException() {
        super();
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super(cause);
    }

    protected SmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
