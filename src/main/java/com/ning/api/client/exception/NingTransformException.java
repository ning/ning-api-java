package com.ning.api.client.exception;

import java.io.IOException;

import com.ning.api.client.NingClientException;

/**
 * Exception type used when root cause is data transformation, such as
 * failure to bind JSON message into expected object(s)
 */
public class NingTransformException extends NingClientException
{
    private static final long serialVersionUID = 1L;

    public NingTransformException(IOException ioe) {
        super(ioe);
    }

    public NingTransformException(String message) {
        super(message);
    }
    
    public NingTransformException(String message, IOException ioe) {
        super(message, ioe);
    }
    
    /**
     * This is tricky: as problem could be related something transient.
     * Most of the time this is more likely to be something more fundamental
     * so let's claim this is not retryable.
     */
    @Override
    public boolean isRetryable() {
        return false;
    }
}
