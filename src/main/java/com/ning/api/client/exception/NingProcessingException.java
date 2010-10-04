package com.ning.api.client.exception;

import com.ning.api.client.NingClientException;

/**
 * Exception used to contain miscellaneous unclassified exception
 * types, where more specific type is not known.
 * In perfect world these exceptions would not be thrown (and indeed
 * goal should be replace any that are seen with more appropriate types),
 * but due to loose coupling it is not possible to statically determine
 * all control flows to eliminate possibility of need for wrapping.
 */
public class NingProcessingException extends NingClientException
{
    private static final long serialVersionUID = 1L;

    public NingProcessingException(Throwable t) {
        super(t);
    }

    public NingProcessingException(String msg, Throwable t) {
        super(msg, t);
    }
    
    /**
     * Since exact type is not known, we can not say for sure, but more likely
     * than not the request can not be retried as is (when wrapping things
     * like NPEs or other severe runtime exceptions)
     */
    @Override
    public boolean isRetryable() {
        return false;
    }
}
