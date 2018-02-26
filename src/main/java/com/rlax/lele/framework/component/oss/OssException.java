package com.rlax.lele.framework.component.oss;

/**
 * Oss 异常
 * @author Rlax
 *
 */
public class OssException extends RuntimeException {

    public OssException() {
        super();
    }

    public OssException(String message) {
        super(message);
    }

    public OssException(String message, Throwable cause) {
        super(message, cause);
    }

    public OssException(Throwable cause) {
        super(cause);
    }

    protected OssException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
