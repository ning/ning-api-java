package com.ning.api.client.exception;

import java.util.concurrent.TimeoutException;

import com.ning.api.client.NingClientException;

public class NingTimeoutException extends NingClientException
{
    private static final long serialVersionUID = 1L;

    protected final long timeoutMsecs;
    
    public NingTimeoutException(TimeoutException e, long timeoutMsecs)
    {
        super("Timed out after "+timeoutMsecs+" msecs: "+e.getMessage(), e);
        this.timeoutMsecs = timeoutMsecs;
    }

    public long getTimeoutMsecs() { return timeoutMsecs; }
    
    /**
     * Regular timeouts are generally retryable, so always returns true
     */
    @Override
    public boolean isRetryable() {
        return true;
    }
}
