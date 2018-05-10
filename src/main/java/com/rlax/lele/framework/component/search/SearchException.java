package com.rlax.lele.framework.component.search;

/**
 * Search 异常
 * @author Rlax
 *
 */
public class SearchException extends RuntimeException {

    public SearchException() {
        super();
    }

    public SearchException(String message) {
        super(message);
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }

    protected SearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
