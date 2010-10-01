package com.ning.api.client.sample;

/**
 * Base class for all exceptions Ning Client throws. Unchecked for convenience;
 * single base class also used to allow catching runtime exceptions that the client
 * library itself throws, compared to errors and other runtime exceptions
 * (like {@link IllegalArgumentException}.
 *
 * @author tatu
 */
@SuppressWarnings("serial")
public abstract class NingClientException extends RuntimeException
{
    protected NingClientException(Throwable cause) {
        super(cause);
    }

    protected NingClientException(String message, Throwable cause) {
        super(message, cause);
    }
    
    protected NingClientException(String message) {
        super(message);
    }

    /**
     * Method that can be called to determine whether request that failed
     * can be retried as is: things like timeouts or (presumable) transient
     * server errors are; whereas client errors and explicit interruptions
     * are not.
     */
    public abstract boolean isRetryable();
}
